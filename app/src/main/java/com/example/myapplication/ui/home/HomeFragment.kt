package com.example.myapplication.ui.home

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        homeViewModel.images.observe(viewLifecycleOwner, Observer { images ->
            (binding.recyclerView.adapter as ImageAdapter).updateImages(images)
            isLoading = false
        })

        loadImages()

        return root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = ImageAdapter(emptyList())

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && !recyclerView.canScrollVertically(1)) {
                    loadImages()
                }
            }
        })
    }

    private fun loadImages() {
        if (!isLoading) {
            isLoading = true
            homeViewModel.fetchImages(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ImageAdapter(private var images: List<ImageData>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val cellSize = Resources.getSystem().displayMetrics.widthPixels / 3 // 3은 column count
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.layoutParams.height = cellSize * 2 // 이전과 같은 높이 설정
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.imageView.setImageURI(image.uri)
        // 이미지 클릭 이벤트 설정
        holder.imageView.setOnClickListener {
            val intent = Intent(holder.imageView.context, Item::class.java)
            intent.putExtra("imageUri", image.uri)
            intent.putExtra("imageDate", image.date)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = images.size

    fun updateImages(newImages: List<ImageData>) {
        images = newImages
        notifyDataSetChanged()
    }
}

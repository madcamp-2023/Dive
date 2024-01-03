package com.example.myapplication.ui.home

import ImageSliderAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private var isLoading = false

    private val imageList : List<Int> = listOf<Int>(
        R.drawable.madcmap, R.drawable.memory3, R.drawable.memory5
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        homeViewModel.images.observe(viewLifecycleOwner, Observer { images ->
            (binding.recyclerView.adapter as ImageAdapter).updateImages(images)
            isLoading = false
        })

        // In your Fragment or Activity
        binding.madcampViewPager.adapter = ImageSliderAdapter(imageList)

        // After the layout has been inflated and the view hierarchy has been created
        binding.recyclerView.post {
            // Get the height of the ImageView
            val imageViewHeight = binding.madcampViewPager.height

            // Set the marginTop of the RecyclerView to be the same as the height of the ImageView
            val layoutParams = binding.recyclerView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = imageViewHeight
            binding.recyclerView.layoutParams = layoutParams
        }

        binding.recyclerView.post {
            val navBarHeight =
                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).height
            binding.recyclerView.setPadding(0, 0, 0, navBarHeight)
        }

        loadImages()

        return root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
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

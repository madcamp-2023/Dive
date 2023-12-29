package com.example.myapplication.ui.home

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.images.observe(
            viewLifecycleOwner,
            Observer { images: List<Int> -> setupGallery(images) })

        root.post {
            val navBarHeight = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).height
            binding.gridLayout.setPadding(0, 0, 0, navBarHeight)
        }

        return root
    }

    private fun setupGallery(images: List<Int>) {
        val gridLayout = binding.gridLayout
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val cellSize = screenWidth / gridLayout.columnCount

        images.forEach { image ->
            val imageView = ImageView(context).apply {
                layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL,1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL,1f)
                ).apply {
                    width = cellSize
                    height = cellSize
                }
                scaleType = ImageView.ScaleType.FIT_CENTER
                setImageResource(image)
                setOnClickListener {
                    val intent = Intent(context, Item::class.java)
                    intent.putExtra("imageId", image)
                    startActivity(intent)
                }
            }
            gridLayout.addView(imageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
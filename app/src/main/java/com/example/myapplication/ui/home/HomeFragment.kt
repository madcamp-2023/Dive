package com.example.myapplication.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
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
    private lateinit var homeViewModel: HomeViewModel
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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


        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupScrollListener()
        homeViewModel.images.observe(
            viewLifecycleOwner,
            Observer { images ->
                setupGallery(images)
                isLoading = false
            }
        )

        if (!isLoading) {
            isLoading = true
            homeViewModel.fetchImages(requireContext())
        }

        root.post {
            val navBarHeight =
                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).height
            binding.gridLayout.setPadding(0, 0, 0, navBarHeight)
        }

        return root
    }

    private fun setupScrollListener() {
        binding.scrollView.setOnScrollChangeListener { v: View, _: Int, _: Int, _: Int, _: Int ->
            val scrollView = v as ScrollView
            val threshold = 400 * resources.displayMetrics.density // dp를 픽셀로 변환
            if (scrollView.getChildAt(0).bottom - (scrollView.height + scrollView.scrollY) <= threshold) {
                if (!isLoading) {
                    isLoading = true
                    homeViewModel.fetchImages(requireContext())
                }
            }
        }
    }

    private fun setupGallery(images: List<ImageData>) {
        val gridLayout = binding.gridLayout
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val cellSize = screenWidth / gridLayout.columnCount

        images.forEach { image ->

            val imageView = ImageView(context).also {
                it.layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                ).apply {
                    width = cellSize
                    height = cellSize
                }
                it.scaleType = ImageView.ScaleType.CENTER_CROP
                it.setImageURI(image.uri)
                it.setOnClickListener {
                    val intent = Intent(context, Item::class.java)
                    intent.putExtra("imageUri", image.uri)
                    intent.putExtra("imageDate", image.date)

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
package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R

class HomeViewModel : ViewModel() {

    private val _images = MutableLiveData<List<Int>>().apply {
        value = listOf(
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
            R.drawable.haejunejung,
        )
    }

    val images: LiveData<List<Int>> = _images
}
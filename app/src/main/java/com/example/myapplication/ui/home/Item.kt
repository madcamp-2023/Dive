package com.example.myapplication.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class Item: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_image)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageSize = getImageSize()
        val imageId = intent.getIntExtra("imageId", 0)

        imageView.layoutParams = LinearLayout.LayoutParams(imageSize, imageSize)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        imageView.setImageResource(imageId)

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}

fun getImageSize(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}
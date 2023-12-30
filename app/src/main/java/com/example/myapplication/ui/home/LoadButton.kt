package com.example.myapplication.ui.home

import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class LoadButton: AppCompatActivity() {

    private lateinit var gridLayout: GridLayout

    val Gallery = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_button)

        gridLayout = findViewById(R.id.gridLayout)

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        fetchImages();
//
//        val loadButton = findViewById<Button>(R.id.load_button)
//        loadButton.setOnClickListener { loadImage() }
    }

    private fun fetchImages() {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val columnIndexID: Int
        val projection = arrayOf(MediaStore.Images.Media._ID)
        var imageUri: Uri

        cursor = applicationContext.contentResolver.query(
            uriExternal, projection, null, null, null
        )

        if (cursor != null) {
            columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val imageId: Long = cursor.getLong(columnIndexID)
                imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId)
                addImageToGridLayout(imageUri)
            }
            cursor.close()
        }
    }

    private fun addImageToGridLayout(imageUri: Uri) {
        val imageView = ImageView(this)
        imageView.layoutParams = GridLayout.LayoutParams().apply {
            width = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
            height = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
            setMargins(8, 8, 8, 8)
        }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageURI(imageUri)
        gridLayout.addView(imageView)
    }

//    private fun loadImage() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//
//        startActivityForResult(Intent.createChooser(intent, "load picture"), Gallery)
//        Toast.makeText(this, "load!", Toast.LENGTH_SHORT).show()
//    }
//
//    @Override
//    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val loadedImage = findViewById<ImageView>(R.id.imageView)
//
//        if (requestCode == Gallery) {
//            if (resultCode == RESULT_OK) {
//                val dataUri = data?.data
//
//                try {
//                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
//                    loadedImage.setImageBitmap(bitmap)
//                } catch (e: Exception) {
//                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
}
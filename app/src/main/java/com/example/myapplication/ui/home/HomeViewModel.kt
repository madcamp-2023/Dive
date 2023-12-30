package com.example.myapplication.ui.home

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _images = MutableLiveData<List<ImageData>>()
    val images: LiveData<List<ImageData>> = _images

    fun fetchImages(context: Context) {
        viewModelScope.launch {
            val imageDataList = mutableListOf<ImageData>()
            val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val cursor: Cursor? = context.contentResolver.query(
                uriExternal, arrayOf(MediaStore.Images.Media._ID), null, null, null, null
            )

            cursor?.use {
                val columnIndexID = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
                    val imageId: Long = it.getLong(columnIndexID)
                    val imageUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageId
                    )
                    val date = getImageDate(context, imageUri)
                    imageDataList.add(ImageData(imageUri, date))
                }
            }
            _images.postValue(imageDataList)
        }
    }

    private fun getImageDate(context: Context, imageUri: Uri): String? {
        context.contentResolver.openInputStream(imageUri).use { inputStream ->
            if (inputStream != null) {
                val exifInterface = ExifInterface(inputStream)
                return exifInterface.getAttribute(ExifInterface.TAG_DATETIME)
            }
        }

        return null
    }
}
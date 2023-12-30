package com.example.myapplication.ui.home

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _images = MutableLiveData<List<Uri>>()
    val images: LiveData<List<Uri>> = _images

    fun fetchImages(context: Context) {
        viewModelScope.launch {
            val imageUris = mutableListOf<Uri>()
            val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val cursor: Cursor? = context.contentResolver.query(
                uriExternal, arrayOf(MediaStore.Images.Media._ID), null, null, null, null
            )

            cursor?.use {
                val columnIndexID = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
                    val imageId: Long = it.getLong(columnIndexID)
                    val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId)
                    imageUris.add(imageUri)
                }
            }
            _images.postValue(imageUris)
        }
    }
}
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _images = MutableLiveData<List<ImageData>>()
    val images: LiveData<List<ImageData>> = _images

    private var currentPage = 0
    private var pageSize = 18

    fun fetchImages(context: Context) {
        viewModelScope.launch {
            val imageDataList = mutableListOf<ImageData>()
            val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val cursor: Cursor? = context.contentResolver.query(
                uriExternal, arrayOf(MediaStore.Images.Media._ID), null, null, null, null
            )

            val offset = currentPage * pageSize
            if (cursor?.moveToPosition(offset) == true) {
                var count = 0
                do {
                    val imageId: Long =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val imageUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageId
                    )
                    val date = getImageDate(context, imageUri)
                    imageDataList.add(ImageData(imageUri, date))
                    count++
                } while (cursor.moveToNext() && count < pageSize)
            }
            cursor?.close()

            val currentList = _images.value.orEmpty()
            _images.postValue(currentList + imageDataList)
            currentPage++
        }
    }

    private suspend fun getImageDate(context: Context, imageUri: Uri): String? {
        return withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(imageUri).use { inputStream ->
                if (inputStream != null) {
                    val exifInterface = ExifInterface(inputStream)
                    exifInterface.getAttribute(ExifInterface.TAG_DATETIME)
                } else {
                    null
                }
            }
        }
    }
}
package com.example.myapplication.ui.home

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _images = MutableLiveData<List<ImageData>>()
    val images: LiveData<List<ImageData>> = _images

    private var currentPage = 0
    private var pageSize = 9

    /**
     * context : 현재 앱의 상태나 시스템에 대한 정보를 담고 있는 객체
     * contentResolver : 앱이 기기의 데이터(미디어 스토어 이미지)에 접근할 수 있도록 해주는 시스템 서비스
     */
    fun fetchImages(context: Context) {
        viewModelScope.launch {
            val newImageList = loadImagesFromMediaStore(context)
            val currentList = _images.value.orEmpty()
            _images.postValue(currentList + newImageList)
            currentPage++
        }
    }

    private suspend fun loadImagesFromMediaStore(context: Context): List<ImageData> {
        return withContext(Dispatchers.IO) {
            queryImages(context).map { imageUri ->
                async {
                    val date = ImageUtils.getImageDate(context, imageUri)
                    ImageData(imageUri, date)
                }
            }.awaitAll()
        }
    }

    private fun queryImages(context: Context) : List<Uri> {
        val uriList = mutableListOf<Uri>()
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)

        context.contentResolver.query(
            uriExternal, projection, null, null, null, null
        )?.use {
            cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val offset = currentPage * pageSize

            if (cursor.moveToPosition(offset)) {
                do {
                    val imageId = cursor.getLong(idColumn)
                    val imageUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageId
                    )

                    uriList.add(imageUri)
                } while (cursor.moveToNext() && uriList.size < pageSize)
            }
        }

        return uriList
    }
}

object ImageUtils {
     suspend fun getImageDate(context: Context, imageUri: Uri): String? {
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
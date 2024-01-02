package com.example.myapplication.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class NotificationsViewModel : ViewModel() {


    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> = _currentIndex

    private val _blogList = MutableLiveData<List<Blog>>()
    val blogList: LiveData<List<Blog>> = _blogList

    init {
        fetchBlog()
    }
    fun fetchBlog() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = "https://fe-developers.kakaoent.com"
            val docs = Jsoup.connect(url).get()
            val aTags = docs.select("a.ThumbnailItem-module--wrapper--0fe61")
            val titleTags = docs.select("h3.ThumbnailItem-module--title--8de6f")
            val descriptionTags = docs.select("p.ThumbnailItem-module--description--825c6")
            val hrefList = aTags.map { it.attr("href") }.toSet().toList()
            val imageSrcList =
                docs.select("div.ThumbnailItem-module--descriptionWrapper--b935b picture img")
                    .map { it.attr("src") }.toSet().toList().filter { it.isNotEmpty() }
            val titleList = titleTags.map { it.text() }.toSet().toList()
            val descriptionList = descriptionTags.map { it.text() }.toSet().toList()

            val blogs = hrefList.indices.map { index ->

                val contentLimited = if (descriptionList[index].length > 40) {
                    descriptionList[index].substring(0, 40)
                } else {
                    descriptionList[index]
                }

                Blog(
                    url = url + imageSrcList.getOrNull(index) ?: "",
                    title = titleList[index],
                    content = contentLimited,
                    page = url + hrefList.getOrNull(index),
                )
            }

            _blogList.postValue(blogs)
            blogs.map { blog ->
                println("$blog\n")
            }
        }
    }
}
package com.example.myapplication.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup


class NotificationsViewModel : ViewModel() {

    val BlogList = listOf<Blog>(
        Blog(
            "https://fe-developers.kakaoent.com/static/2df14188500484f68dd135abf91a1ccc/c512e/thumbnail.webp",
            "ChatGPT는 FE개발자를 대체할 수 있을까?",
            "ChatGPT를 사용해 간단한 웹툰 서비스를 개발해보았습니다.",
            "https://fe-developers.kakaoent.com/2023/230323-chatgpt-and-fe-developer/"
        ), Blog(
            "https://fe-developers.kakaoent.com/static/a4ec19a437a15839c79c5aa145fc47f2/c512e/thumbnail.webp",
            "테스트 코드 자동으로 만들기 feat. Cypress Studio",
            "테스트 코드를 자동으로 만들 수 있는 Cypress Studio의 사용 방법을 공유합니다. 특히 비 개발자에게 로우코드(Low Code)로 자동화 테스트를 구축하는 데 도움이 될 것입니다.",
            "https://fe-developers.kakaoent.com/2022/221222-cypress-studio-test-automation-low-code/"
        ), Blog(
            "https://fe-developers.kakaoent.com/static/cb3e6f6668b3da767109cb21a2d1f74a/c512e/thumbnail.webp",
            "프론트엔드와 THE TWELVE-FACTOR APP",
            "The Twelve-Factor와 Next.js 를 통해 만드는 유연하고 견고한 웹 애플리케이션",
            "https://fe-developers.kakaoent.com/2021/211125-create-12factor-app-with-nextjs/"
        )
    )

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> = _currentIndex

    fun moveToPrevious() {
        if (_currentIndex.value!! > 0) {
            _currentIndex.value = _currentIndex.value!! - 1
        }
    }

    fun moveToNext() {
        if (_currentIndex.value!! < BlogList.size - 1) {
            _currentIndex.value = _currentIndex.value!! + 1
        }
    }

    fun getCurrentPageUrl(): String? {
        return _currentIndex.value?.let { i ->
            if (i in BlogList.indices) {
                BlogList[i].page
            } else {
                null
            }
        }
    }
}
package com.example.koview.presentation.ui.main.global.createreview

import androidx.lifecycle.ViewModel
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.presentation.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CreateReviewFragmentViewModel @Inject constructor() : ViewModel(){


    var content = MutableStateFlow("")

    var link = MutableStateFlow("")

    private val _purchaseLinkList = MutableStateFlow<List<PurchaseLinkDTO>>(emptyList())
    val purchaseLinkList: StateFlow<List<PurchaseLinkDTO>> = _purchaseLinkList.asStateFlow()

    private val _imageLinkList = MutableStateFlow<List<String>>(emptyList())
    val imageLinkList: StateFlow<List<String>> = _imageLinkList.asStateFlow()

    fun inputImage(newImages: List<String>){
        // 기존 목록에서 새 이미지 중복 체크 후 추가
        val existingImages = _imageLinkList.value
        val filteredImages = newImages.filterNot { existingImages.contains(it) }

        // 중복이 없는 새 이미지를 기존 목록에 추가
        _imageLinkList.value = existingImages + filteredImages
    }

    fun deleteImage(url: String){
        // 현재 리스트에서 url를 제외한 새로운 리스트 생성
        val updatedList = _imageLinkList.value.filter { it != url }

        _imageLinkList.value = updatedList
    }

    fun inputLink(){
        val tag = extractTag(link.value)
        val newLink = PurchaseLinkDTO(link.value, tag)

        // 기존 목록에 새 링크 추가
        _purchaseLinkList.value = _purchaseLinkList.value + newLink
        link.value = ""
    }

    fun deleteLink(link: PurchaseLinkDTO) {
        // 현재 리스트에서 link를 제외한 새로운 리스트 생성
        val updatedList = _purchaseLinkList.value.filter { it != link }

        _purchaseLinkList.value = updatedList
    }

    // URL로부터 도메인 이름 추출하는 함수
    private fun extractTag(link: String): String {
        // 정규 표현식을 사용하여 www.과 .com 사이의 문자열을 검색
        val regex = """https?://(www\.)?([^/]+)\.com(/.*)?""".toRegex()
        val matchResult = regex.find(link)

        return if (matchResult != null) {
            matchResult.groups[2]?.value ?: ""  // 그룹 2에서 도메인 이름 반환
        } else {
            ""  // 매치되지 않는 경우 빈 문자열 반환
        }
    }


}
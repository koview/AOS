package com.example.koview.presentation.ui.main.global.createreview

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.CreateReviewRequest
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.data.model.response.ImageDTO
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.toMultiPartImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

sealed class CreateReviewEvent {
    data class ShowToastMessage(val msg: String) : CreateReviewEvent()
    data object NavigateToBack : CreateReviewEvent()
    data object ShowLoading : CreateReviewEvent()
    data object DismissLoading : CreateReviewEvent()
}

@HiltViewModel
class CreateReviewFragmentViewModel @Inject constructor(
    private val repository: MainRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {


    private val _event = MutableSharedFlow<CreateReviewEvent>()
    val event: SharedFlow<CreateReviewEvent> = _event.asSharedFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked.asStateFlow()

    var content = MutableStateFlow("")

    var link = MutableStateFlow("")

    var createBtnOn = MutableStateFlow(false)

    private val _purchaseLinkList = MutableStateFlow<List<PurchaseLinkDTO>>(emptyList())
    val purchaseLinkList: StateFlow<List<PurchaseLinkDTO>> = _purchaseLinkList.asStateFlow()

    private val _imageLinkList = MutableStateFlow<List<Uri>>(emptyList())
    val imageLinkList: StateFlow<List<Uri>> = _imageLinkList.asStateFlow()

    private var imagePathList: List<ImageDTO> = emptyList()


    fun createBtnClick() {
        viewModelScope.launch {
            if (content.value != "" && _purchaseLinkList.value.isNotEmpty()) {
                // 로딩 띄우기
                _event.emit(CreateReviewEvent.ShowLoading)

                if (_imageLinkList.value.isEmpty()) {
                    createReview()
                } else {
                    postReviewImage()
                }
            } else {
                _event.emit(CreateReviewEvent.ShowToastMessage("\"내용 혹은 링크를 다시 확인해주세요.\""))
            }
        }

    }

    private fun convertUriToMultipart(uri: Uri): MultipartBody.Part? {
        return uri.toMultiPartImage(context)
    }

    private fun postReviewImage() {
        viewModelScope.launch {

            // _imageLinkList.value를 List<MultipartBody.Part>로 변환
            val images: List<MultipartBody.Part> = _imageLinkList.value.mapNotNull { uri ->
                convertUriToMultipart(uri)
            } ?: emptyList()
            Log.d("postReviewImage", "images.isEmpty(): ${images.isEmpty()}")

            repository.postReviewImage(images).let {
                when (it) {
                    is BaseState.Error -> {
                        _event.emit(CreateReviewEvent.DismissLoading)
                        _event.emit(CreateReviewEvent.ShowToastMessage(it.msg))
                    }

                    is BaseState.Success -> {
                        imagePathList = it.body.result
                        Log.d("PostReviewImage Success", imagePathList.toString())

                        createReview()
                    }
                }
            }
        }
    }

    private fun createReview() {
        val contentValue = content.value
        val imageListValue: List<Long> = imagePathList.map { it -> it.imageId } ?: emptyList()
        val purchaseLinkList = _purchaseLinkList.value

        viewModelScope.launch {
            val request = CreateReviewRequest(contentValue, imageListValue, purchaseLinkList)
            repository.createReview(request).let {
                when (it) {
                    is BaseState.Error -> {
                        _event.emit(CreateReviewEvent.DismissLoading)
                        _event.emit(CreateReviewEvent.ShowToastMessage(it.msg))
                    }

                    is BaseState.Success -> {
                        _event.emit(CreateReviewEvent.DismissLoading)
                        navigateToBack()
                    }
                }
            }
        }
    }

    fun inputImage(newImages: List<Uri>) {
        // 기존 목록에서 새 이미지 중복 체크 후 추가
        val existingImages = _imageLinkList.value
        val filteredImages = newImages.filterNot { existingImages.contains(it) }

        // 중복이 없는 새 이미지를 기존 목록에 추가
        _imageLinkList.value = existingImages + filteredImages
    }

    fun deleteImage(url: String) {
        // 현재 리스트에서 url를 제외한 새로운 리스트 생성
        val updatedList = _imageLinkList.value.filter { it.toString() != url }

        _imageLinkList.value = updatedList
    }

    // 링크 추가
    fun inputLink() {
        viewModelScope.launch {
            val trimmedLink = trimUrl(link.value)  // 링크를 자르는 함수 호출
            val tag = extractTag(trimmedLink)
            val newLink = PurchaseLinkDTO(trimmedLink, tag)

            if (tag == "") {
                _event.emit(CreateReviewEvent.ShowToastMessage("유효하지 않은 링크입니다."))
            } else {
                // 기존 목록에 새 링크 추가
                _purchaseLinkList.value = _purchaseLinkList.value + newLink
            }
            link.value = ""
        }
    }

    // 링크 삭제
    fun deleteLink(link: PurchaseLinkDTO) {
        // 현재 리스트에서 link를 제외한 새로운 리스트 생성
        val updatedList = _purchaseLinkList.value.filter { it != link }

        _purchaseLinkList.value = updatedList
    }

    // URL 쿼리 파라미터 잘라내는 함수
    private fun trimUrl(url: String): String {
        val uri = Uri.parse(url)

        val trimmedUrl = "${uri.scheme}://${uri.host}${uri.path}"

        return trimmedUrl
    }

    // URL로부터 도메인 이름 추출하는 함수
    private fun extractTag(link: String): String {
        // 정규 표현식을 사용하여 www.과 .com 사이의 문자열을 검색
        val regex = """https?://(www\.)?([^/]+)\.com(/.*)?""".toRegex()
        val matchResult = regex.find(link)

        return if (matchResult != null) {
            // 그룹 2에서 도메인 이름 반환
            matchResult.groups[2]?.value ?: ""
        } else {
            // 매치되지 않는 경우 빈 문자열 반환
            ""
        }
    }

    fun validate() {
        if (content.value == "") {
            createBtnOn.value = false
            return
        } else if (_purchaseLinkList.value.isEmpty()) {
            createBtnOn.value = false
            return
        } else {
            createBtnOn.value = true
            return
        }
    }

    fun onClickLike() {
        _isLiked.value = !_isLiked.value
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(CreateReviewEvent.NavigateToBack)
        }
    }
}
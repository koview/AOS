package com.example.koview.presentation.ui.main.ask.post

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.CreateQueryRequest
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.data.model.response.ImageDTO
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.toMultiPartImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AskPostEvent() {
    data object GoToGallery : AskPostEvent()
    data class ShowToastMessage(val msg: String) : AskPostEvent()
    data object NavigateToBack : AskPostEvent()
    data object CreateQuery : AskPostEvent()
}

data class AskPostImageUiState(
    val imageList: List<Uri> = emptyList(),
)

data class AskPostLinkUiState(
    val shopLinkList: List<PurchaseLinkDTO> = emptyList(),
)

@HiltViewModel
class AskPostViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<AskPostEvent>()
    val event: SharedFlow<AskPostEvent> = _event.asSharedFlow()

    private val _imageUiState = MutableStateFlow(AskPostImageUiState())
    val imageUiState: StateFlow<AskPostImageUiState> = _imageUiState.asStateFlow()

    private val _linkUiState = MutableStateFlow(AskPostLinkUiState())
    val linkUiState: StateFlow<AskPostLinkUiState> = _linkUiState.asStateFlow()

    var imagePathList: List<Long> = emptyList()

    val content = MutableStateFlow("")
    var link = MutableStateFlow("")

    // 버튼 활성화
    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                imageUiState,
                linkUiState,
                content
            ) { imageState, linkState, contentText ->
                // 조건: 이미지 리스트, 링크 리스트, content, link가 모두 비어있지 않을 때만 버튼 활성화
                imageState.imageList.isNotEmpty() &&
                        linkState.shopLinkList.isNotEmpty() &&
                        contentText.isNotBlank()
            }.collect { isEnabled ->
                _isButtonEnabled.value = isEnabled
            }
        }
    }

    fun goToGallery() {
        viewModelScope.launch {
            _event.emit(AskPostEvent.GoToGallery)
        }
    }

    // 이미지 업로드
    fun postReviewImage(context: Context) {
        viewModelScope.launch {
            try {
                // Uri를 파일로 변환하여 MultipartBody.Part로 만들기
                val imageParts = _imageUiState.value.imageList.mapNotNull { uri ->
                    uri.toMultiPartImage(context)
                }

                if (imageParts.isNotEmpty()) {
                    // 서버로 이미지 전송
                    repository.postQueryImages(imageParts).let {
                        when (it) {
                            is BaseState.Error -> {
                                _event.emit(AskPostEvent.ShowToastMessage(it.msg))
                            }

                            is BaseState.Success -> {
                                // 이미지 정보 저장
                                imagePathList = it.body.result.map {
                                    it.imageId
                                }

                                createQuery()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _event.emit(AskPostEvent.ShowToastMessage("이미지 업로드 중 오류가 발생했습니다."))
            }
        }
    }

    // 질문 글 작성
    private fun createQuery() {
        viewModelScope.launch {
            val request = CreateQueryRequest(content.value, imagePathList, linkUiState.value.shopLinkList)
            repository.postQuery(request).let {
                when(it){
                    is BaseState.Success -> {
                        _event.emit(AskPostEvent.NavigateToBack)
                    }
                    is BaseState.Error -> {
                        // todo: 이미지 리스트 삭제 api 호풀
                        _event.emit(AskPostEvent.ShowToastMessage(it.msg))
                    }
                }
            }
        }
    }

    // 이미지 추가
    fun addImage(uris: List<Uri>) {
        viewModelScope.launch {
            _imageUiState.update { state ->
                state.copy(
                    imageList = state.imageList + uris // 이미지 추가
                )
            }
            Log.d("질문", "이미지 추가 완료")
        }
    }

    // 이미지 삭제
    fun deleteImage(uri: Uri) {
        viewModelScope.launch {
            _imageUiState.update { state ->
                state.copy(
                    imageList = state.imageList.filterNot { it == uri } // 해당 uri와 일치하는 이미지를 제거
                )
            }
        }
    }

    // 링크 추가
    fun addLink() {
        viewModelScope.launch {
            val tag = extractTag(link.value)
            val newLink = PurchaseLinkDTO(link.value, tag)

            if (tag == "") {
                _event.emit(AskPostEvent.ShowToastMessage("유효하지 않은 링크입니다."))
            } else {
                _linkUiState.update { state ->
                    state.copy(
                        shopLinkList = state.shopLinkList + newLink // 링크 추가
                    )
                }
            }
            link.value = ""
        }
    }

    // 링크 삭제
    fun deleteLink(link: PurchaseLinkDTO) {
        viewModelScope.launch {
            _linkUiState.update { state ->
                state.copy(
                    shopLinkList = state.shopLinkList.filterNot { it.purchaseLink == link.purchaseLink } // 해당 링크와 일치하는 요소 제거
                )
            }
        }
    }

    // URL로부터 도메인 이름 추출
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

    // 작성 완료 버튼 클릭
    fun onCreateQueryClick() {
        viewModelScope.launch {
            _event.emit(AskPostEvent.CreateQuery)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(AskPostEvent.NavigateToBack)
        }
    }
}
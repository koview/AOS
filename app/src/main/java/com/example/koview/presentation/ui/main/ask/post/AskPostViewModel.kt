package com.example.koview.presentation.ui.main.ask.post

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AskPostEvent() {
    data object GoToGallery : AskPostEvent()
}

data class AskPostUiState(
    val imageList: List<Uri> = emptyList(),
)

@HiltViewModel
class AskPostViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AskPostEvent>()
    val event: SharedFlow<AskPostEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(AskPostUiState())
    val uiState: StateFlow<AskPostUiState> = _uiState.asStateFlow()

    val content = MutableStateFlow("")

    fun goToGallery() {
        viewModelScope.launch {
            _event.emit(AskPostEvent.GoToGallery)
        }
    }

    // 이미지 추가
    fun addImage(uris: List<Uri>) {
        viewModelScope.launch {
            _uiState.update { state ->
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
            _uiState.update { state ->
                state.copy(
                    imageList = state.imageList.filterNot { it == uri } // 해당 uri와 일치하는 이미지를 제거
                )
            }
        }
    }


}
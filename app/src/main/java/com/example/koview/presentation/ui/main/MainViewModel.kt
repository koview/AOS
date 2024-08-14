package com.example.koview.presentation.ui.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

sealed class MainEvent {
    data object GoToGetImage : MainEvent()
    data class ShowToastMessage(val msg: String) : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

    private val _imageUri = MutableSharedFlow<Uri>()
    val imageUri: SharedFlow<Uri> = _imageUri.asSharedFlow()

    // 이미지 권한 확인 및 갤러리 열기
    fun goToSetProfileImage() {
        viewModelScope.launch {
            _event.emit(MainEvent.GoToGetImage)
        }
    }

    fun setImageUri(uri: Uri) {
        viewModelScope.launch {
            _imageUri.emit(uri)
        }
    }

    // 이미지 업로드
    fun fileToUrl(file: MultipartBody.Part) {
        viewModelScope.launch {
            // todo: 이미지 업로드 api 연결
        }
    }

}
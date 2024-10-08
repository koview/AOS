package com.example.koview.presentation.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainEvent {
    data object GoToGetImage : MainEvent()
    data class ShowToastMessage(val msg: String) : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

    private val _imageList = MutableSharedFlow<List<Uri>>()
    val imageList: SharedFlow<List<Uri>> = _imageList.asSharedFlow()

    // 이미지 권한 확인 및 갤러리 열기
    fun goToSetProfileImage() {
        viewModelScope.launch {
            _event.emit(MainEvent.GoToGetImage)
        }
    }

    fun setImageUri(uris: List<Uri>) {
        viewModelScope.launch {
            _imageList.emit(uris)
        }
    }

}
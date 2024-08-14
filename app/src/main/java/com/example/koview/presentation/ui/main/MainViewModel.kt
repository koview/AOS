package com.example.koview.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.mypage.MypageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainEvent {
    data class ShowToastMessage(val msg: String) : MainEvent()
    data object GetGallery : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

    var imageList = MutableStateFlow<List<String>>(emptyList())// 이미지 URI를 문자열로 저장할 리스트

    fun getImageToGallery(){
        viewModelScope.launch {
            _event.emit(MainEvent.GetGallery)
        }
    }

}
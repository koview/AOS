package com.example.koview.presentation.ui.main.mypage.reviewdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MyPageDetailEvent {
    data object NavigateToBack : MyPageDetailEvent()
}

@HiltViewModel
class MyPageReviewDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MyPageDetailEvent>()
    val event: SharedFlow<MyPageDetailEvent> = _event.asSharedFlow()

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(MyPageDetailEvent.NavigateToBack)
        }
    }
}
package com.example.koview.presentation.ui.main.ask.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AskPostEvent() {
    data object GoToGallery : AskPostEvent()
}

@HiltViewModel
class AskPostViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AskPostEvent>()
    val event: SharedFlow<AskPostEvent> = _event.asSharedFlow()

    val content = MutableStateFlow("")

    fun goToGallery() {
        viewModelScope.launch {
            _event.emit(AskPostEvent.GoToGallery)
        }
    }

}
package com.example.koview.presentation.ui.main.ask.askdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.global.productdetail.ProductDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AskDetailEvent {
    data object NavigateToAsk : AskDetailEvent()
    data object NavigateToAskAnswer : AskDetailEvent()
}

@HiltViewModel
class AskDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AskDetailEvent>()
    val event: SharedFlow<AskDetailEvent> = _event.asSharedFlow()

    fun navigateToAsk() {
        viewModelScope.launch {
            _event.emit(AskDetailEvent.NavigateToAsk)
        }
    }

    fun navigateToAskAnswer() {
        viewModelScope.launch {
            _event.emit(AskDetailEvent.NavigateToAskAnswer)
        }
    }
}
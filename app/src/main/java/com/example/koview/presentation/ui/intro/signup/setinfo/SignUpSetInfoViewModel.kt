package com.example.koview.presentation.ui.intro.signup.setinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SetInfoEvent {
    data object NavigateToBack : SetInfoEvent()
    data object NavigateToComplete : SetInfoEvent()
}

@HiltViewModel
class SignUpSetInfoViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SetInfoEvent>()
    val event: SharedFlow<SetInfoEvent> = _event.asSharedFlow()

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(SetInfoEvent.NavigateToBack)
        }
    }

    fun navigateToNext() {
        viewModelScope.launch {
            _event.emit(SetInfoEvent.NavigateToComplete)
        }
    }
}
package com.example.koview.presentation.ui.intro.signup.complete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SignUpCompleteEvent {
    data object NavigateToLogin : SignUpCompleteEvent()
}

@HiltViewModel
class SignUpCompleteViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SignUpCompleteEvent>()
    val event: SharedFlow<SignUpCompleteEvent> = _event.asSharedFlow()

    fun navigateToLogin() {
        viewModelScope.launch {
            _event.emit(SignUpCompleteEvent.NavigateToLogin)
        }
    }
}
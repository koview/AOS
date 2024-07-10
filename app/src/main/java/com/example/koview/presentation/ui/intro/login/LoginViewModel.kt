package com.example.koview.presentation.ui.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginEvent {
    data object NavigateToMainActivity : LoginEvent()
    data object NavigateToSignUp : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun checkLogin() {
        // todo : 로그인 로직 추가
        navigateToMain()
    }

    private fun navigateToMain() {
        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToMainActivity)
        }
    }

    fun navigateToSignUp() {
        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToSignUp)
        }
    }
}
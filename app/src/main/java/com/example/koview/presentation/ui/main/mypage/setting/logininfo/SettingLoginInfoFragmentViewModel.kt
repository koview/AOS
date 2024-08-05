package com.example.koview.presentation.ui.main.mypage.setting.logininfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LoginInfoEvent {
    data object NavigateToBack : LoginInfoEvent()
}

class SettingLoginInfoFragmentViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<LoginInfoEvent>()
    val event: SharedFlow<LoginInfoEvent> = _event.asSharedFlow()

    private val _nickname = MutableStateFlow("네로")
    val nickname: StateFlow<String> = _nickname.asStateFlow()


    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(LoginInfoEvent.NavigateToBack)
        }
    }
}
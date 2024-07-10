package com.example.koview.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashEvent {
    data object NavigateToMainActivity : SplashEvent()
    data object NavigateToIntroActivity : SplashEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event.asSharedFlow()

    fun checkLogin() {
        // todo: 로그인 정보 있으면 바로 MainActivity로 이동
        viewModelScope.launch {
//            _event.emit(SplashEvent.NavigateToIntroActivity)
            _event.emit(SplashEvent.NavigateToMainActivity)
        }
    }
}
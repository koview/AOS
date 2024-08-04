package com.example.koview.presentation.ui.main.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.repository.IntroRepository
import com.example.koview.presentation.ui.intro.login.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class MypageEvent {
    data object NavigateToSetting : MypageEvent()
}

@HiltViewModel
class MyPageFragmentViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MypageEvent>()
    val event: SharedFlow<MypageEvent> = _event.asSharedFlow()


    fun navigateToSetting() {
        viewModelScope.launch {
            _event.emit(MypageEvent.NavigateToSetting)
        }
    }
}
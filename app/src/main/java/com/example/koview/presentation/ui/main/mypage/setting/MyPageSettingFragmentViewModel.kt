package com.example.koview.presentation.ui.main.mypage.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.intro.signup.setinfo.SetInfoEvent
import com.example.koview.presentation.ui.main.home.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class MypageSettingEvent {

    data object NavigateToPoint : MypageSettingEvent()
    data object NavigateToLoginInfo : MypageSettingEvent()
    data object NavigateToOperating : MypageSettingEvent()
    data object NavigateToBack : MypageSettingEvent()
}

@HiltViewModel
class MyPageSettingFragmentViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MypageSettingEvent>()
    val event: SharedFlow<MypageSettingEvent> = _event.asSharedFlow()


    fun navigateToPoint() {
        viewModelScope.launch {
            _event.emit(MypageSettingEvent.NavigateToPoint)
        }
    }
    fun navigateToLoginInfo() {
        viewModelScope.launch {
            _event.emit(MypageSettingEvent.NavigateToLoginInfo)
        }
    }
    fun navigateToOperating() {
        viewModelScope.launch {
            _event.emit(MypageSettingEvent.NavigateToOperating)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(MypageSettingEvent.NavigateToBack)
        }
    }
}
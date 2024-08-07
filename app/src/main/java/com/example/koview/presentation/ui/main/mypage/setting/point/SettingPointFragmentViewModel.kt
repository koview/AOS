package com.example.koview.presentation.ui.main.mypage.setting.point

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

sealed class PointEvent {
    data object NavigateToBack : PointEvent()
}

class SettingPointFragmentViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<PointEvent>()
    val event: SharedFlow<PointEvent> = _event.asSharedFlow()


    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(PointEvent.NavigateToBack)
        }
    }
}
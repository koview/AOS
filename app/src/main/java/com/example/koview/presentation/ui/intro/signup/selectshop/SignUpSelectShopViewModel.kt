package com.example.koview.presentation.ui.intro.signup.selectshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SelectShopEvent {
    data object NavigateToBack : SelectShopEvent()
    data object NavigateToSetInfo : SelectShopEvent()
}

@HiltViewModel
class SignUpSelectShopViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SelectShopEvent>()
    val event: SharedFlow<SelectShopEvent> = _event.asSharedFlow()

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(SelectShopEvent.NavigateToBack)
        }
    }

    fun navigateToNext() {
        viewModelScope.launch {
            _event.emit(SelectShopEvent.NavigateToSetInfo)
        }
    }
}
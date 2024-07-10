package com.example.koview.presentation.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

sealed class MainEvent {
    data class ShowToastMessage(val msg: String) : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

}
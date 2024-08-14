package com.example.koview.presentation.ui.main.ask.post

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AskPostViewModel @Inject constructor(): ViewModel() {

    val content = MutableStateFlow("")

}
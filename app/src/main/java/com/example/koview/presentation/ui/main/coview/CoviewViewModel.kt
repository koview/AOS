package com.example.koview.presentation.ui.main.coview

import androidx.lifecycle.ViewModel
import com.example.koview.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoviewViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

}
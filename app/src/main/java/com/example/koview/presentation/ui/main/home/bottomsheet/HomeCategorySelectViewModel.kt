package com.example.koview.presentation.ui.main.home.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.home.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeCategorySelectEvent {
    data class ChangeCategory(val curCategory: Category): HomeCategorySelectEvent()
    data class ApplySelectedCategory(val filter: Category?) : HomeCategorySelectEvent()
}

@HiltViewModel
class HomeCategorySelectViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<HomeCategorySelectEvent>()
    val event: SharedFlow<HomeCategorySelectEvent> = _event.asSharedFlow()

    private lateinit var category: Category

    fun setCategory(curCategory: Category) {
        viewModelScope.launch {
            category = curCategory
            _event.emit(HomeCategorySelectEvent.ChangeCategory(curCategory))
        }
    }

    fun selectCategory() {
        viewModelScope.launch {
            _event.emit(HomeCategorySelectEvent.ApplySelectedCategory(category))
        }
    }

}
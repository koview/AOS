package com.example.koview.presentation.ui.main.home.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

sealed class HomeCategorySelectEvent {
    data class ApplySelectedCategory(val filter: Category?) : HomeCategorySelectEvent()
}

@HiltViewModel
class HomeCategorySelectViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<HomeCategorySelectEvent>()
    val event: SharedFlow<HomeCategorySelectEvent> = _event.asSharedFlow()

    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category.asStateFlow()

    fun setCategory(curCategory: Category) {
        viewModelScope.launch {
            _category.value = curCategory
        }
    }

    fun selectCategory() {
        viewModelScope.launch {
            _event.emit(HomeCategorySelectEvent.ApplySelectedCategory(category.value))
        }
    }

}
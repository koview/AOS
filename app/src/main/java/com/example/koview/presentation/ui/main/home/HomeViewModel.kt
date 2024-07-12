package com.example.koview.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.home.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeEvent {
    data object ShowCategoryBottomSheet : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    private val _category = MutableStateFlow(Category.ACCESSORIES)
    val category: StateFlow<Category> = _category

    fun showCategoryBottomSheet() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowCategoryBottomSheet)
        }
    }

    fun applyFilter(filter: Category) {
        viewModelScope.launch {
            // todo : 해당 카테고리의 상품들로 정보 다시 불러오기
            _category.value = filter
        }
    }
}
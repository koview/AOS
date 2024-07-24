package com.example.koview.presentation.ui.main.home.search.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.home.search.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductDetailEvent {
    data object NavigateToSearch : ProductDetailEvent()
}
@HiltViewModel
class ProductDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<ProductDetailEvent>()
    val event: SharedFlow<ProductDetailEvent> = _event.asSharedFlow()

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(ProductDetailEvent.NavigateToSearch)
        }
    }
}
package com.example.koview.presentation.ui.main.global.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductEvent {
    data class NavigateToProductDetail(val searchProduct: SearchProduct) : ProductEvent()
    data class ClickTag(val productUrl: String?) : ProductEvent()
}

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {

    private var _searchProduct = MutableLiveData<SearchProduct>()
    val searchProduct: LiveData<SearchProduct> get() = _searchProduct

    private val _event = MutableSharedFlow<ProductEvent>()
    val event: SharedFlow<ProductEvent> = _event.asSharedFlow()

    private val _searchProductUrl = MutableLiveData<String?>()
    val searchProductUrl: LiveData<String?> get() = _searchProductUrl

    fun navigateToProductDetail(searchProduct: SearchProduct) {
        _searchProduct.value = searchProduct
        viewModelScope.launch {
            _event.emit(ProductEvent.NavigateToProductDetail(searchProduct))
        }
    }

    fun clickTag(url: String?) {
        _searchProductUrl.value = url
        viewModelScope.launch {
            _event.emit(ProductEvent.ClickTag(url))
        }
    }
}
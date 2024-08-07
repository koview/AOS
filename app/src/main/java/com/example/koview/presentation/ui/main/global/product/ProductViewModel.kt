package com.example.koview.presentation.ui.main.global.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.presentation.ui.main.global.product.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductEvent {
    data class NavigateToProductDetail(val searchProduct: SingleProduct) : ProductEvent()
}

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {

    private var _searchProduct = MutableLiveData<SingleProduct>()
    val searchProduct: LiveData<SingleProduct> get() = _searchProduct

    private val _event = MutableSharedFlow<ProductEvent>()
    val event: SharedFlow<ProductEvent> = _event.asSharedFlow()

    fun navigateToProductDetail(searchProduct: SingleProduct) {
        _searchProduct.value = searchProduct
        viewModelScope.launch {
            _event.emit(ProductEvent.NavigateToProductDetail(searchProduct))
        }
    }

}
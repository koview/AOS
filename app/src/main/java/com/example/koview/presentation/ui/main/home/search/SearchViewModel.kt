package com.example.koview.presentation.ui.main.home.search

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

sealed class SearchEvent {
    data class NavigateToProductDetail(val searchProduct: SearchProduct) : SearchEvent()
}

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private var _searchProductList = MutableLiveData<List<SearchProduct>>()
    val searchProductList: LiveData<List<SearchProduct>> get() = _searchProductList

    private var _searchProduct = MutableLiveData<SearchProduct>()
    val searchProduct: LiveData<SearchProduct> get() = _searchProduct

    private val _event = MutableSharedFlow<SearchEvent>()
    val event: SharedFlow<SearchEvent> = _event.asSharedFlow()


    fun navigateToProductDetail(searchProduct: SearchProduct) {
        _searchProduct.value = searchProduct
        viewModelScope.launch {
            _event.emit(SearchEvent.NavigateToProductDetail(searchProduct))
        }
    }

//    fun getSearchProduct(): LiveData<SearchProduct> {
//        return _searchProduct
//    }

    fun setSearchProduct(searchProduct: SearchProduct) {
        _searchProduct.value = searchProduct
    }

}
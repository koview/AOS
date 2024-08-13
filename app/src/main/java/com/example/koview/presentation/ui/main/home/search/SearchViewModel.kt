package com.example.koview.presentation.ui.main.home.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.data.model.response.Status
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.global.product.model.Product
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

sealed class SearchEvent {
    data object NavigateToHome : SearchEvent()
}

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _searchProductList = MutableStateFlow<List<Product>>(emptyList())
    val searchProductList: StateFlow<List<Product>> = _searchProductList.asStateFlow()

    private val _event = MutableSharedFlow<SearchEvent>()
    val event: SharedFlow<SearchEvent> = _event.asSharedFlow()

    private val _getProducts = MutableStateFlow<List<SingleProduct>>(emptyList())
    val getProducts: StateFlow<List<SingleProduct>> = _getProducts.asStateFlow()

    private val _searchProdImage = MutableStateFlow<List<String>>(emptyList())
    val searchProdImage: StateFlow<List<String>> = _searchProdImage.asStateFlow()

    private val _searchTerm = MutableStateFlow<String?>("")
    val searchTerm : StateFlow<String?> = _searchTerm.asStateFlow()

    init {
        getProducts()
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _event.emit(SearchEvent.NavigateToHome)
        }
    }

    fun setSearchTerm(term: String?) {
        viewModelScope.launch {
            _searchTerm.value = term
        }
    }

    fun getProducts(searchTerm: String? = null, category: Category? = null) {
        val selectedCategory = if (category == Category.ALL) {
            null
        } else {
            category
        }
        viewModelScope.launch {
            repository.getProducts(
                status = Status.FAMOUS,
                category = selectedCategory,
                searchTerm = searchTerm,
                page = 1,
                size = 20
            ).let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("SearchFragment", "GetProducts ERROR(Request Success)")
                        Log.d("SearchFragment", it.code + ", " + it.msg)
                    }

                    is BaseState.Success -> {
                        _getProducts.value = it.body.result.productList
                        Log.d("SearchFragment", category.toString())


                    }
                }
            }
        }
    }
}
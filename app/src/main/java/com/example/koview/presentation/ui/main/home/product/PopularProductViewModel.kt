package com.example.koview.presentation.ui.main.home.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.data.model.response.Status
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.global.product.model.Product
import com.example.koview.presentation.ui.main.global.product.model.Review
import com.example.koview.presentation.ui.main.global.product.model.TagShop
import com.example.koview.presentation.ui.main.home.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularProductViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _popularProductList = MutableStateFlow<List<Product>>(emptyList())
    val popularProductList: StateFlow<List<Product>> = _popularProductList

    private val _getProducts = MutableStateFlow<List<SingleProduct>>(emptyList())
    val getProducts: StateFlow<List<SingleProduct>> = _getProducts.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            repository.getProducts(
                status = Status.FAMOUS,
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
                    }
                }
            }
        }
    }
}
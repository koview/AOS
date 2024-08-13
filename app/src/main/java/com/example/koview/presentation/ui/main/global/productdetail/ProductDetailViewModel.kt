package com.example.koview.presentation.ui.main.global.productdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.ProductReviewDetail
import com.example.koview.data.model.response.ReviewDetailList
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.home.search.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductDetailEvent {
    data object NavigateToSearch : ProductDetailEvent()
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _event = MutableSharedFlow<ProductDetailEvent>()
    val event: SharedFlow<ProductDetailEvent> = _event.asSharedFlow()

    private val _getReviews= MutableStateFlow<List<ProductReviewDetail>>(emptyList())
    val getReviews: StateFlow<List<ProductReviewDetail>> = _getReviews.asStateFlow()

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(ProductDetailEvent.NavigateToSearch)
        }
    }

    fun getReviewDetails(productId: Long) {
        viewModelScope.launch {
            repository.getProductReview(
                productId = productId
            ).let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("ProductDetailFragment", "GetReviewDetails ERROR(Request Success)")
                        Log.d("ProductDetailFragment", it.code + ", " + it.msg)
                    }
                    is BaseState.Success -> {
                        _getReviews.value = it.body.result.reviewPaging.reviewList
                    }
                }
            }
        }
    }
}
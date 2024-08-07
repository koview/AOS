package com.example.koview.presentation.ui.main.home.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.ProductsResult
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.data.model.response.Status
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.global.product.model.Product
import com.example.koview.presentation.ui.main.global.product.model.Review
import com.example.koview.presentation.ui.main.global.product.model.TagShop
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
//    private val _getProducts = MutableStateFlow(ProductsResult())
//    val getProducts: StateFlow<ProductsResult> = _getProducts.asStateFlow()


    init {
//        setProductListData()
        getProducts()
    }

    private fun setProductListData() {
        // todo: 상품 검색 데이터 가져오기
        val fetchedData = listOf(
            Product(
                title = "얏호",
                imageUrl = "",
                reviewNumber = 1,
                registDate = "2024-07-12",
                isWarning = true,
                isHot = false,
                category = Category.TOY,
                shopList = listOf(
                    TagShop(
                        title = "xpadfnejnnddf",
                        productUrl = "https://www.naver.com/",
                        isVerify = false
                    ),
                    TagShop(
                        title = "xpadfnejnnddf",
                        productUrl = "https://papago.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "xpadfnejnnddfdfsdfsdf",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop 1",
                        productUrl = "https://www.naver.com/",
                        isVerify = false
                    ),
                    TagShop(
                        title = "Shop 2",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop 3",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop 4",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop 5",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop 6",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop B",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop A",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    ),
                    TagShop(
                        title = "Shop B",
                        productUrl = "https://www.naver.com/",
                        isVerify = true
                    )
                ),
                reviewList = listOf(
//                Review(
//                    nickname = "네로",
//                    content = "테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명",
//                    imageUrl = listOf(
//                        "https://ifh.cc/g/f9WcP4.jpg",
//                        "https://ifh.cc/g/f9WcP4.jpg"
//                    ),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                ),
//                Review(
//                    nickname = "ddddd",
//                    content = "설명입니당",
//                    imageUrl = listOf(
//                        "https://ifh.cc/g/f9WcP4.jpg",
//                    ),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                ),
//                Review(
//                    nickname = "2323",
//                    content = "설명입니당",
//                    imageUrl = listOf(),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                ),
//                Review(
//                    nickname = "sssss",
//                    content = "설명입니당",
//                    imageUrl = listOf(
//                        "https://ifh.cc/g/f9WcP4.jpg",
//                        "https://ifh.cc/g/f9WcP4.jpg"
//                    ),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                )
                )
            ),
            Product(
                title = "어라어라얼",
                imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
                reviewNumber = 5,
                registDate = "2024-07-13",
                isWarning = false,
                isHot = true,
                category = Category.SANITARY,
                shopList = listOf(

                ),
                reviewList = listOf(
                    Review(
                        nickname = "커너",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "ddddd",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "2323",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "sssss",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg",
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    )
                )
            ),
            Product(
                title = "dd",
                imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
                reviewNumber = 5,
                registDate = "2024-07-13",
                isWarning = false,
                isHot = false,
                category = Category.ACCESSORIES,
                shopList = listOf(
                    TagShop(title = "Shop C", productUrl = "www.naver.com", isVerify = true),
                    TagShop(title = "Shop D", productUrl = "www.naver.com", isVerify = true),
                    TagShop(title = "Shop d", productUrl = "www.naver.com", isVerify = true)
                ),
                reviewList = listOf(
                    Review(
                        nickname = "멜리",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "ddddd",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg",
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "2323",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg",
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "sssss",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    )
                )
            ),
            Product(
                title = "dd",
                imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
                reviewNumber = 5,
                registDate = "2024-07-13",
                isWarning = true,
                isHot = false,
                category = Category.STATIONARY,
                shopList = listOf(
                    TagShop(title = "Shop C", productUrl = "www.naver.com", isVerify = false),
                    TagShop(title = "Shop D", productUrl = "www.naver.com", isVerify = false),
                    TagShop(title = "Shop d", productUrl = "www.naver.com", isVerify = false)
                ),
                reviewList = listOf(
                    Review(
                        nickname = "네로",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "ddddd",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "2323",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    ),
                    Review(
                        nickname = "sssss",
                        content = "설명입니당",
                        imageUrl = listOf(
                            "https://ifh.cc/g/f9WcP4.jpg"
                        ),
                        likeNumber = 10,
                        commentNumber = 20,
                        date = "2024-07-13"
                    )
                )
            )
        )
        _searchProductList.value = fetchedData
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _event.emit(SearchEvent.NavigateToHome)
        }
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
//                        val getProducts: ProductsResult = it.body.result
                        _getProducts.value = it.body.result.productList
                    }
                }
            }
        }
    }

}
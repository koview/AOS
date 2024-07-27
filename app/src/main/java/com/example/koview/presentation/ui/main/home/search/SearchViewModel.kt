package com.example.koview.presentation.ui.main.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.home.model.Category
import com.example.koview.presentation.ui.main.home.search.model.Review
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct
import com.example.koview.presentation.ui.main.home.search.model.TagShop
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
    data class NavigateToProductDetail(val searchProduct: SearchProduct) : SearchEvent()
    data object NavigateToHome : SearchEvent()
}

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _searchProductList = MutableStateFlow<List<SearchProduct>>(emptyList())
    val searchProductList: StateFlow<List<SearchProduct>> = _searchProductList.asStateFlow()

    private var _searchProduct = MutableLiveData<SearchProduct>()
    val searchProduct: LiveData<SearchProduct> get() = _searchProduct

    private val _event = MutableSharedFlow<SearchEvent>()
    val event: SharedFlow<SearchEvent> = _event.asSharedFlow()

    init {
        setProductListData()
    }

    private fun setProductListData() {
        // todo: 상품 검색 데이터 가져오기
        val fetchedData = listOf(
            SearchProduct(
                title = "얏호",
                imageUrl = "",
                reviewNumber = 1,
                registDate = "2024-07-12",
                isWarning = true,
                isHot = false,
                category = Category.TOY,
                shopList = listOf(
                    TagShop(title = "xpadfnejnnddf", isVerify = false),
                    TagShop(title = "xpadfnejnnddf", isVerify = true),
                    TagShop(title = "xpadfnejnnddfdfsdfsdf", isVerify = true),
                    TagShop(title = "Shop 1", isVerify = false),
                    TagShop(title = "Shop 2", isVerify = true),
                    TagShop(title = "Shop 3", isVerify = true),
                    TagShop(title = "Shop 4", isVerify = true),
                    TagShop(title = "Shop 5", isVerify = true),
                    TagShop(title = "Shop 6", isVerify = true),
                    TagShop(title = "Shop B", isVerify = true),
                    TagShop(title = "Shop A", isVerify = true),
                    TagShop(title = "Shop B", isVerify = true)
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
            SearchProduct(
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
            SearchProduct(
                title = "dd",
                imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
                reviewNumber = 5,
                registDate = "2024-07-13",
                isWarning = false,
                isHot = false,
                category = Category.ACCESSORIES,
                shopList = listOf(
                    TagShop(title = "Shop C", isVerify = true),
                    TagShop(title = "Shop D", isVerify = true),
                    TagShop(title = "Shop d", isVerify = true)
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
            SearchProduct(
                title = "dd",
                imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
                reviewNumber = 5,
                registDate = "2024-07-13",
                isWarning = true,
                isHot = false,
                category = Category.STATIONARY,
                shopList = listOf(
                    TagShop(title = "Shop C", isVerify = false),
                    TagShop(title = "Shop D", isVerify = false),
                    TagShop(title = "Shop d", isVerify = false)
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

    fun navigateToProductDetail(searchProduct: SearchProduct) {
        _searchProduct.value = searchProduct
        viewModelScope.launch {
            _event.emit(SearchEvent.NavigateToProductDetail(searchProduct))
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _event.emit(SearchEvent.NavigateToHome)
        }
    }

    fun search() {
        // TODO: searchAPI 연동
    }

}
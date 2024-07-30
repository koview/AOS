package com.example.koview.presentation.ui.main.home.product

import androidx.lifecycle.ViewModel
import com.example.koview.presentation.ui.main.home.model.Category
import com.example.koview.presentation.ui.main.home.search.model.Review
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct
import com.example.koview.presentation.ui.main.home.search.model.TagShop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HarmfulProductViewModel @Inject constructor() : ViewModel() {

    private val _harmfulProductList = MutableStateFlow<List<SearchProduct>>(emptyList())
    val harmfulProductList: StateFlow<List<SearchProduct>> = _harmfulProductList

    init {
        setHarmfulProductListData()
    }

    private fun setHarmfulProductListData() {
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
            SearchProduct(
                title = "어라어라얼",
                imageUrl = "https://ifh.cc/g/s1nljr.jpg",
                reviewNumber = 5,
                registDate = "2024-07-13",
                isWarning = true,
                isHot = false,
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
                imageUrl = "https://ifh.cc/g/s1nljr.jpg",
                reviewNumber = 5,
                registDate = "2024-07-13",
                isWarning = true,
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
            SearchProduct(
                title = "dd",
                imageUrl = "https://ifh.cc/g/s1nljr.jpg",
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
        _harmfulProductList.value = fetchedData
    }
}
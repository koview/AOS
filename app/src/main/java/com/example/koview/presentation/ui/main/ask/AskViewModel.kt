package com.example.koview.presentation.ui.main.ask

import androidx.lifecycle.ViewModel
import com.example.koview.presentation.ui.main.ask.model.AskData
import com.example.koview.presentation.ui.main.global.product.model.TagShop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AskViewModel @Inject constructor() : ViewModel() {

    private val _askList = MutableStateFlow<List<AskData>>(emptyList())
    val askList: StateFlow<List<AskData>> = _askList.asStateFlow()

    init {
        setAskListData()
    }

    private fun setAskListData() {
        val fetchedData = listOf(
            AskData(
                title = "이 목걸이 안전한가요?",
                contents = "dfdfd",
                viewCount = 12,
                answerCount = 23,
                askCount = 34,
                askImage = "https://ifh.cc/g/f9WcP4.jpg",
                nickname = "멜",
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
                    )
                )
            ), AskData(
                title = "이 목걸이 안전한가요?",
                contents = "dfdfd",
                viewCount = 12,
                answerCount = 23,
                askCount = 34,
                askImage = "https://ifh.cc/g/f9WcP4.jpg",
                nickname = "멜",
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
                    )
                )
            ), AskData(
                title = "이 목걸이 안전한가요?",
                contents = "dfdfd",
                viewCount = 12,
                answerCount = 23,
                askCount = 34,
                askImage = "https://ifh.cc/g/f9WcP4.jpg",
                nickname = "멜",
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
                    )
                )
            )
        )
        _askList.value = fetchedData
    }
}
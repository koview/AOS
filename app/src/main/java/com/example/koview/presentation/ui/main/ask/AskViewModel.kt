package com.example.koview.presentation.ui.main.ask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.ask.model.AskData
import com.example.koview.presentation.ui.main.global.product.ProductEvent
import com.example.koview.presentation.ui.main.global.product.model.Product
import com.example.koview.presentation.ui.main.global.product.model.Review
import com.example.koview.presentation.ui.main.global.product.model.TagShop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AskEvent {
    data class NavigateToAskDetail(val askDetail: AskData) : AskEvent()
}

@HiltViewModel
class AskViewModel @Inject constructor() : ViewModel() {

    private val _askList = MutableStateFlow<List<AskData>>(emptyList())
    val askList: StateFlow<List<AskData>> = _askList.asStateFlow()

    private val _event = MutableSharedFlow<AskEvent>()
    val event: SharedFlow<AskEvent> = _event.asSharedFlow()

    private var _askDetail = MutableLiveData<AskData>()
    val askDetail: LiveData<AskData> get() = _askDetail

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
                createdAt = "2024-08-05",
                isAsk = false,
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
                ), reviewList = listOf(
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
            ), AskData(
                title = "이 장난감 안전한가요?",
                contents = "dfdfd",
                viewCount = 12,
                answerCount = 23,
                askCount = 34,
                askImage = "https://ifh.cc/g/f9WcP4.jpg",
                nickname = "멜",
                createdAt = "2024-08-05",
                isAsk = true,
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
                ), reviewList = listOf(
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
            ), AskData(
                title = "이 튜브 안전한가요?",
                contents = "dfdfd",
                viewCount = 12,
                answerCount = 23,
                askCount = 34,
                askImage = "https://ifh.cc/g/f9WcP4.jpg",
                nickname = "멜",
                createdAt = "2024-08-05",
                isAsk = false,
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
                ), reviewList = listOf(
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
        _askList.value = fetchedData
    }

    fun navigateToAskDetail(askDetail: AskData) {
        _askDetail.value = askDetail
        viewModelScope.launch {
            _event.emit(AskEvent.NavigateToAskDetail(askDetail))
        }
    }

    fun onAskClick(item: AskData) {
        viewModelScope.launch {

            val updatedItem = item.copy(
                isAsk = !item.isAsk,
                askCount = item.askCount + if (item.isAsk) -1 else 1
            )


            _askList.update { list ->
                list.map {
                    if (it == item) updatedItem else it
                }
            }
        }
    }
}
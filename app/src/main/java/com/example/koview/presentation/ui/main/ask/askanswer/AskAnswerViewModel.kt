package com.example.koview.presentation.ui.main.ask.askanswer

import androidx.lifecycle.ViewModel
import com.example.koview.presentation.ui.main.global.product.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AskAnswerViewModel @Inject constructor() : ViewModel() {

    private val _reviewList = MutableStateFlow<List<Review>>(emptyList())
    val reviewList: StateFlow<List<Review>> = _reviewList

    init {
        setMyReviewListData()
    }

    private fun setMyReviewListData() {
        val fetchedData = listOf(
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
        _reviewList.value = fetchedData
    }
}
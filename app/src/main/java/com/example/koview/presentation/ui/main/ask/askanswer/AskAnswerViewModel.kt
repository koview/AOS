package com.example.koview.presentation.ui.main.ask.askanswer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.QueryAnswerRequest
import com.example.koview.data.model.response.ReviewList
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.global.product.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AskAnswerViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _reviewList = MutableStateFlow<List<ReviewList>>(emptyList())
    val reviewList: StateFlow<List<ReviewList>> = _reviewList

    init {
        getMyReviews()
    }

//    private fun setMyReviewListData() {
//        val fetchedData = listOf(
//            Review(
//                nickname = "네로",
//                content = "설명입니당",
//                imageUrl = listOf(
//                    "https://ifh.cc/g/f9WcP4.jpg"
//                ),
//                likeNumber = 10,
//                commentNumber = 20,
//                date = "2024-07-13"
//            ),
//            Review(
//                nickname = "ddddd",
//                content = "설명입니당",
//                imageUrl = listOf(
//                    "https://ifh.cc/g/f9WcP4.jpg"
//                ),
//                likeNumber = 10,
//                commentNumber = 20,
//                date = "2024-07-13"
//            ),
//            Review(
//                nickname = "2323",
//                content = "설명입니당",
//                imageUrl = listOf(
//                    "https://ifh.cc/g/f9WcP4.jpg"
//                ),
//                likeNumber = 10,
//                commentNumber = 20,
//                date = "2024-07-13"
//            ),
//            Review(
//                nickname = "sssss",
//                content = "설명입니당",
//                imageUrl = listOf(
//                    "https://ifh.cc/g/f9WcP4.jpg"
//                ),
//                likeNumber = 10,
//                commentNumber = 20,
//                date = "2024-07-13"
//            )
//        )
//        _reviewList.value = fetchedData
//    }

    fun postQueryAnswer(queryId: Long, params: QueryAnswerRequest) {
        viewModelScope.launch {
            repository.postQueryAnswer(queryId, params).let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("AskAnswerFragment", "PostQueryAnswer ERROR(Request Success)")
                        Log.d("AskAnswerFragment", it.code + ", " + it.msg)
                    }

                    is BaseState.Success -> {
                        Log.d("AskAnswerFragment", queryId.toString() + params.toString())
                    }
                }
            }
        }
    }

    fun getMyReviews() {
        viewModelScope.launch {
            repository.getMyReviews(
                page = 1,
                size = 20
            ).let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("AskAnswerFragment", "GetMyReviews ERROR(Request Success)")
                        Log.d("AskAnswerFragment", it.code + ", " + it.msg)
                    }
                    is BaseState.Success -> {
                        _reviewList.value = it.body.result.reviewList
                    }
                }
            }
        }
    }
}
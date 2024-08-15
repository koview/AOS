package com.example.koview.presentation.ui.main.ask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.QueryResultList
import com.example.koview.data.repository.MainRepository
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
    data class NavigateToAskDetail(val askDetail: QueryResultList) : AskEvent()
    data object NavigateToPost : AskEvent()
}

@HiltViewModel
class AskViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _event = MutableSharedFlow<AskEvent>()
    val event: SharedFlow<AskEvent> = _event.asSharedFlow()

    private var _askDetail = MutableLiveData<QueryResultList>()
    val askDetail: LiveData<QueryResultList> get() = _askDetail

    private val _getQueries = MutableStateFlow<List<QueryResultList>>(emptyList())
    val getQueries: StateFlow<List<QueryResultList>> = _getQueries.asStateFlow()

    private val _answerLike = MutableStateFlow<Boolean>(false)
    val answerLike: StateFlow<Boolean> = _answerLike.asStateFlow()

    init {
        getQueries()
    }

    fun getQueries() {
        viewModelScope.launch {
            repository.getQueries().let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("AskFragment", "GetQueries ERROR(Request Success)")
                        Log.d("AskFragment", it.code + ", " + it.msg)
                    }

                    is BaseState.Success -> {
                        _getQueries.value = it.body.result.queryList
                    }
                }
            }
        }
    }

    fun navigateToAskDetail(askDetail: QueryResultList) {
        _askDetail.value = askDetail
        viewModelScope.launch {
            _event.emit(AskEvent.NavigateToAskDetail(askDetail))
        }
    }

//    fun onLikeClick(review: Review) {
//        viewModelScope.launch {
//            // Review의 isLiked 값을 업데이트
//            val updatedReview = review.copy(
//                isLiked = !review.isLiked,
//                likeNumber = if (review.isLiked) review.likeNumber - 1 else review.likeNumber + 1
//            )
//
//            // _askList 업데이트
//            _askList.update { askList ->
//                askList.map { askData ->
//                    if (askData == _askDetail.value) {
//                        val updatedReviewList = askData.reviewList.map {
//                            if (it == review) updatedReview else it
//                        }
//                        askData.copy(reviewList = updatedReviewList)
//                    } else {
//                        askData
//                    }
//                }
//            }
//
//            // _askDetail 업데이트
//            _askDetail.value = _askDetail.value?.copy(
//                reviewList = _askDetail.value?.reviewList?.map {
//                    if (it == review) updatedReview else it
//                } ?: emptyList()
//            )
//        }
//    }

    fun postWithQuery(item: QueryResultList) {
        viewModelScope.launch {
            val result = if (item.isWithQuery) {
                repository.deleteWithQuery(item.queryId)
            } else {
                repository.postWithQuery(item.queryId)
            }

            when (result) {
                is BaseState.Success -> {
                    val updatedItem = item.copy(
                        isWithQuery = !item.isWithQuery,
                        totalWithQueryCount = if (item.isWithQuery) {
                            item.totalWithQueryCount - 1
                        } else {
                            item.totalWithQueryCount + 1
                        }
                    )

                    _getQueries.update { currentList ->
                        currentList.map {
                            if (it.queryId == updatedItem.queryId) {
                                updatedItem
                            } else {
                                it
                            }
                        }
                    }

                    _askDetail.value = updatedItem
                }

                is BaseState.Error -> {
                    Log.d("AskDetailFragment", "ERROR(Request Success)")
                }
            }
        }
    }

    fun updateAnswer(item: QueryResultList) {
        viewModelScope.launch {
            val updatedItem = item.copy(
                totalAnswerCount = item.totalAnswerCount + 1
            )

            _getQueries.update { currentList ->
                currentList.map {
                    if (it.queryId == updatedItem.queryId) {
                        updatedItem
                    } else {
                        it
                    }
                }
            }

            _askDetail.value = updatedItem
        }
    }


    fun navigateToPost() {
        viewModelScope.launch {
            _event.emit(AskEvent.NavigateToPost)
        }
    }
}
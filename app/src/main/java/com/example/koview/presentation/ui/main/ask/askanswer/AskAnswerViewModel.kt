package com.example.koview.presentation.ui.main.ask.askanswer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.QueryAnswerRequest
import com.example.koview.data.model.response.ReviewList
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.ask.askdetail.AskDetailEvent
import com.example.koview.presentation.ui.main.global.product.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AskAnswerEvent {
    data object NavigateToAskDetail : AskAnswerEvent()
}
@HiltViewModel
class AskAnswerViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _event = MutableSharedFlow<AskAnswerEvent>()
    val event: SharedFlow<AskAnswerEvent> = _event.asSharedFlow()

    private val _reviewList = MutableStateFlow<List<ReviewList>>(emptyList())
    val reviewList: StateFlow<List<ReviewList>> = _reviewList


    private val _response = MutableStateFlow<Boolean>(false)
    val response: StateFlow<Boolean> = _response.asStateFlow()

    init {
        getMyReviews()
    }

    fun postQueryAnswer(queryId: Long, params: QueryAnswerRequest) {
        viewModelScope.launch {
            repository.postQueryAnswer(queryId, params).let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("AskAnswerFragment", "PostQueryAnswer ERROR(Request Success)")
                        Log.d("AskAnswerFragment", it.code + ", " + it.msg)
                    }

                    is BaseState.Success -> {
                        Log.d("AskAnswerFragment", it.body.code)
                        _response.value = it.body.isSuccess
                        _event.emit(AskAnswerEvent.NavigateToAskDetail)
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

    fun navigateToAskDetail() {
        viewModelScope.launch {
            _event.emit(AskAnswerEvent.NavigateToAskDetail)
        }
    }
}
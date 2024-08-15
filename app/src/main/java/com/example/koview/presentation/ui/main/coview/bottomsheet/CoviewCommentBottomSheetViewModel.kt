package com.example.koview.presentation.ui.main.coview.bottomsheet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.CoviewCommentRequest
import com.example.koview.data.model.response.CoviewCommentItem
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

data class CoviewCommentUiState(
    val page: Int = 1,
    val hasNext: Boolean = true,
    val commentList: List<CoviewCommentItem>? = emptyList(),
    val isCommentListEmpty: Boolean = false,
)

sealed class CoviewCommentEvent {
    data class ShowToastMessage(val msg: String) : CoviewCommentEvent()
    data object ShowLoading : CoviewCommentEvent()
    data object DismissLoading : CoviewCommentEvent()
    data class AddCommentCount(val reviewId: Long) : CoviewCommentEvent()
}

@HiltViewModel
class CoviewCommentBottomSheetViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CoviewCommentUiState())
    val uiState: StateFlow<CoviewCommentUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<CoviewCommentEvent>()
    val event: SharedFlow<CoviewCommentEvent> = _event.asSharedFlow()

    val comment = MutableStateFlow("")

    // 댓글 가져오기
    fun getComment(reviewId: Long) {
        viewModelScope.launch {
            if (uiState.value.hasNext) {
                // 로딩 띄우기
                _event.emit(CoviewCommentEvent.ShowLoading)

                repository.getCoviewComments(reviewId, uiState.value.page, 15).let {
                    when (it) {
                        is BaseState.Success -> {
                            val comments = it.body.result.commentList

                            _uiState.update { state ->
                                state.copy(
                                    page = state.page + 1,
                                    hasNext = it.body.result.hasNext,
                                    commentList = (state.commentList ?: emptyList()) + (comments
                                        ?: emptyList()), // 기존 리스트에 새로운 리뷰 추가
                                    isCommentListEmpty = comments.isNullOrEmpty()
                                )
                            }

                            Log.d("댓글", "isListEmpty : ${uiState.value.isCommentListEmpty}")
                        }

                        is BaseState.Error -> {
                            _event.emit(CoviewCommentEvent.ShowToastMessage(it.msg))
                        }
                    }
                }
                _event.emit(CoviewCommentEvent.DismissLoading)
            }
        }
    }

    // 댓글 작성
    fun addComment(reviewId: Long) {
        viewModelScope.launch {
            val request = CoviewCommentRequest(comment.value)
            // 로딩 띄우기
            _event.emit(CoviewCommentEvent.ShowLoading)

            repository.addCoviewComment(reviewId, request).let {
                when (it) {
                    is BaseState.Success -> {

                        _uiState.update { state ->
                            state.copy(
                                page = 1,
                                hasNext = true,
                                commentList = emptyList(),
                                isCommentListEmpty = false
                            )
                        }

                        // 댓글 다시 불러오기
                        getComment(reviewId)

                        // 댓글 개수 증가
                        _event.emit(CoviewCommentEvent.AddCommentCount(reviewId))
                    }

                    is BaseState.Error -> {
                        _event.emit(CoviewCommentEvent.ShowToastMessage(it.msg))
                    }
                }
            }
            comment.emit("")
            _event.emit(CoviewCommentEvent.DismissLoading)
        }
    }
}
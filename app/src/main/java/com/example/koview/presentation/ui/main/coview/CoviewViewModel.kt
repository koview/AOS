package com.example.koview.presentation.ui.main.coview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
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

data class CoviewUiState(
    val page: Int = 1,
    val hasNext: Boolean = true,
    val reviewList: List<CoviewUiData> = emptyList(),
)

sealed class CoviewEvent {
    data class ShowToastMessage(val msg: String) : CoviewEvent()
    data object ShowLoading : CoviewEvent()
    data object DismissLoading : CoviewEvent()
}

@HiltViewModel
class CoviewViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CoviewUiState())
    val uiState: StateFlow<CoviewUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<CoviewEvent>()
    val event: SharedFlow<CoviewEvent> = _event.asSharedFlow()

    var profileImgUrl: String? = ""

    // 댓글 입력 창에 보여줄 프로필 사진
    fun getUserInfo() {
        viewModelScope.launch {
            repository.getMyDetail().let {
                when (it) {
                    is BaseState.Success -> {
                        profileImgUrl = it.body.result.url
                    }

                    is BaseState.Error -> {
                    }
                }
            }
        }

        _uiState.update { state ->
            state.copy(
                page = 1,
                hasNext = true,
                reviewList = emptyList()
            )
        }

        getReviews()
    }

    fun getReviews() {
        viewModelScope.launch {
            if (uiState.value.hasNext) {
                // 로딩 띄우기
                _event.emit(CoviewEvent.ShowLoading)

                repository.getCoviewReviews(uiState.value.page, 15).let {
                    when (it) {
                        is BaseState.Success -> {
                            val reviews = it.body.result.reviewList.map { data ->
                                data.toCoviewUiData(
                                    myProfileImgUrl = profileImgUrl
                                )
                            }
                            _uiState.update { state ->
                                state.copy(
                                    page = state.page + 1,
                                    hasNext = it.body.result.hasNext,
                                    reviewList = state.reviewList + reviews // 기존 리스트에 새로운 리뷰 추가
                                )
                            }
                        }

                        is BaseState.Error -> {
                            _event.emit(CoviewEvent.ShowToastMessage(it.msg))
                        }
                    }
                    _event.emit(CoviewEvent.DismissLoading)
                }
            }
        }
    }

    fun onLikeClick(item: CoviewUiData) {
        viewModelScope.launch {
            // todo: 좋아요 업데이트 api 호출
            val updatedItem = item.copy(isLiked = !item.isLiked)

            // 좋아요 누른 리뷰 _uiState 업데이트
            _uiState.update { state ->
                state.copy(
                    reviewList = state.reviewList.map {
                        if (it.reviewId == item.reviewId) {
                            updatedItem
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }
}
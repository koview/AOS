package com.example.koview.presentation.ui.main.coview

import android.util.Log
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
    val keyword = MutableStateFlow("")

    private val _isSearchMode = MutableStateFlow(false)
    val isSearchMode: StateFlow<Boolean> = _isSearchMode.asStateFlow()


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

    // 코뷰 리뷰 검색 세팅
    fun initSearchReviews() {
        viewModelScope.launch {
            // 초기화
            _uiState.update { state ->
                state.copy(
                    page = 1,
                    hasNext = true,
                    reviewList = emptyList()
                )
            }
            _isSearchMode.value = true

            searchReviews()
        }
    }

    // 코뷰 리뷰 검색
    fun searchReviews() {
        viewModelScope.launch {
            if (isSearchMode.value && uiState.value.hasNext) {
                repository.searchCoviewReviews(keyword.value, uiState.value.page, 15).let {
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
                }
            }
        }
    }

    // 다음 페이지가 없으면 리뷰 전체 호출 모드로 변경
    fun checkSearchMode() {
        if (!uiState.value.hasNext) {
            _isSearchMode.value = false
        }
        //Log.d("코뷰", "[상태 확인] 리뷰 검색 hasNext ${uiState.value.hasNext} -> 검색 모드 ${isSearchMode.value}")
    }

    fun resetKeyword() {
        keyword.value = ""
    }

    fun onLikeClick(item: CoviewUiData) {
        viewModelScope.launch {
            // 현재 좋아요 상태에 따라 API 호출
            val result = if (item.isLiked) {
                repository.deleteReviewLike(item.reviewId)
            } else {
                repository.addReviewLike(item.reviewId)
            }

            when (result) {
                is BaseState.Success -> {
                    // 좋아요 상태 업데이트
                    val updatedItem = item.copy(
                        isLiked = !item.isLiked,
                        totalLikeCount = if (item.isLiked) {
                            item.totalLikeCount - 1
                        } else {
                            item.totalLikeCount + 1
                        }
                    )
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

                is BaseState.Error -> {
                    _event.emit(CoviewEvent.ShowToastMessage(result.msg))
                }
            }
        }
    }

    fun addCommentCount(reviewId: Long) {
        viewModelScope.launch {
            // 댓글 개수 증가
            _uiState.update { state ->
                // reviewId와 일치하는 리뷰를 찾기
                val updatedReviewList = state.reviewList.map { review ->
                    if (review.reviewId == reviewId) {
                        review.copy(totalCommentCount = review.totalCommentCount + 1)
                    } else {
                        review
                    }
                }

                // 업데이트된 리스트로 상태를 갱신
                state.copy(reviewList = updatedReviewList)
            }
        }
    }
}
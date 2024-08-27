package com.example.koview.presentation.ui.main.global.reviewdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
import com.example.koview.presentation.ui.main.global.toCoviewUiData
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

data class UserReviewDetailUiState(
    val page: Int = 1,
    val hasNext: Boolean = true,
    val reviewList: List<CoviewUiData> = emptyList(),
)

sealed class UserReviewDetailEvent {
    data class ShowToastMessage(val msg: String) : UserReviewDetailEvent()
    data object ShowLoading : UserReviewDetailEvent()
    data object DismissLoading : UserReviewDetailEvent()
    data object NavigateToBack : UserReviewDetailEvent()
}

@HiltViewModel
class UserReviewDetailViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserReviewDetailUiState())
    val uiState: StateFlow<UserReviewDetailUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<UserReviewDetailEvent>()
    val event: SharedFlow<UserReviewDetailEvent> = _event.asSharedFlow()

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    var profileImgUrl: String? = ""

    // 상단 이름 설정
    fun setNickname(name: String) {
        _nickname.value = "${name}의 리뷰"
    }

    // 댓글 입력 창에 보여줄 프로필 사진
    fun getUserInfo(reviewId: Long, memberId: Long) {
        viewModelScope.launch {
            repository.getMyDetail().let {
                when (it) {
                    is BaseState.Success -> {
                        profileImgUrl = it.body.result.imageUrl
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

        getReviews(reviewId, memberId)
    }

    fun getReviews(reviewId: Long, memberId: Long) {
        viewModelScope.launch {
            if (uiState.value.hasNext) {
                // 로딩 띄우기
                _event.emit(UserReviewDetailEvent.ShowLoading)

                // todo: memberId 전달받아 넣어주기
                repository.getReviewDetails(uiState.value.page, 15, reviewId, memberId).let {
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
                            _event.emit(UserReviewDetailEvent.ShowToastMessage(it.msg))
                        }
                    }
                    _event.emit(UserReviewDetailEvent.DismissLoading)
                }
            }
        }
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
                    _event.emit(UserReviewDetailEvent.ShowToastMessage(result.msg))
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

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(UserReviewDetailEvent.NavigateToBack)
        }
    }
}
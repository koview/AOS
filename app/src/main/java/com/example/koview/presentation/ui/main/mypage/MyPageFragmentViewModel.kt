package com.example.koview.presentation.ui.main.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.DeleteMyReviewRequest
import com.example.koview.data.model.response.MyReview
import com.example.koview.data.model.response.ReviewList
import com.example.koview.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class MypageEvent {
    data object NavigateToSetting : MypageEvent()
    data object NavigateToCreateReview : MypageEvent()
    data object ShowLoading : MypageEvent()
    data object DismissLoading : MypageEvent()
}

@HiltViewModel
class MyPageFragmentViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<MypageEvent>()
    val event: SharedFlow<MypageEvent> = _event.asSharedFlow()

    // 리뷰 삭제 버튼 활성화
    private val _isChecking = MutableStateFlow(false)
    val isChecking: StateFlow<Boolean> = _isChecking.asStateFlow()

    // 삭제할 리뷰 리스트
    private val _deleteReviewList = MutableStateFlow<List<Long>>(emptyList())
    val deleteReviewList: StateFlow<List<Long>> = _deleteReviewList.asStateFlow()

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    private val _profileImg = MutableStateFlow("")
    val profileImg: StateFlow<String> = _profileImg.asStateFlow()

    private val _memberId = MutableStateFlow(1L)
    val memberId: StateFlow<Long> = _memberId.asStateFlow()

    private val _isEmpty = MutableStateFlow(true)
    val isEmpty: StateFlow<Boolean> = _isEmpty.asStateFlow()

    // 리뷰 리스트와 현재 페이지 추가
    private val _myReviews = MutableStateFlow<List<MyReview>>(emptyList())
    val myReviews: StateFlow<List<MyReview>> = _myReviews.asStateFlow()

    private var currentPage = 1
    private val pageSize = 10 // 페이지당 리뷰 수
    var hasNext = true

    fun getMyDetail() {
        viewModelScope.launch {
            repository.getMyDetail().let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("MyPageFragment", it.code.toString() + ", " + it.msg.toString())
                    }

                    is BaseState.Success -> {
                        _nickname.value = it.body.result.memberNickname
                        _profileImg.value = it.body.result.imageUrl
                        _memberId.value = it.body.result.memberId
                    }
                }
            }
        }
    }

    fun getMyReviews(isReload: Boolean = false) {
        viewModelScope.launch {
            if (isReload) {
                currentPage = 1 // 페이지 초기화
            } else if (!hasNext) {
                Log.d("MyPageFragment", "다음 페이지가 없습니다.")
                return@launch // 다음 페이지가 없으면 조기 반환

            }

            // 로딩 띄우기
            _event.emit(MypageEvent.ShowLoading)

            val response = repository.getMyReviews(page = currentPage, size = pageSize)

            when (response) {
                is BaseState.Error -> {
                    Log.d("MyPageFragment", response.code + ", " + response.msg)
                }

                is BaseState.Success -> {
                    // ReviewList를 MyReview로 변환
                    val newReviews = response.body.result.reviewList.map { convertToMyReview(it) }
                    if (isReload) {
                        // 삭제 후 새로 로딩하는 경우
                        _myReviews.value = newReviews // 전체 리스트로 교체
                    } else {
                        // 기존 리스트에 추가
                        _myReviews.value = _myReviews.value + newReviews
                    }

                    // 현재 페이지 증가
                    currentPage++
                    // 다음 페이지 존재 여부 업데이트
                    hasNext = response.body.result.hasNext // 이 값을 업데이트
                }
            }

            _event.emit(MypageEvent.DismissLoading)
        }
    }

    fun deleteMyReviews() {
        viewModelScope.launch {
            val request = DeleteMyReviewRequest(_deleteReviewList.value)
            val response = repository.deleteMyReviews(request)

            when (response) {
                is BaseState.Error -> {
                    Log.d("MyPageFragment", response.code + ", " + response.msg)
                }

                is BaseState.Success -> {
                    _isChecking.value = false
                    getMyReviews(isReload = true) // 삭제 후 리뷰를 처음부터 새로 가져오기
                }
            }
        }
    }

    // ReviewList를 MyReview로 변환하는 함수
    private fun convertToMyReview(reviewList: ReviewList): MyReview {
        return MyReview(
            memberId = memberId.value,
            reviewId = reviewList.reviewId,
            content = reviewList.content,
            writer = reviewList.profileInfo.memberNickname,
            imageList = reviewList.imageList,
            totalCommentCount = reviewList.totalCommentCount,
            totalLikesCount = reviewList.totalLikesCount,
            isLiked = reviewList.isLiked,
            createdAt = reviewList.createdAt,
            updatedAt = reviewList.updatedAt
        )
    }


    fun startChecking(reviewId: Long) {
        _isChecking.value = true
        toggleReviewId(reviewId)
    }

    fun toggleReviewId(reviewId: Long): Boolean {
        changeSelectedById(reviewId)
        if (_deleteReviewList.value.contains(reviewId)) {
            // 아이디가 이미 존재하면 제거
            _deleteReviewList.value = _deleteReviewList.value.filter { it != reviewId }
        } else {
            // 아이디가 존재하지 않으면 추가
            _deleteReviewList.value = _deleteReviewList.value + reviewId
        }

        if (_deleteReviewList.value.isEmpty()) {
            _isChecking.value = false // 리스트가 비어있으면 체크 상태를 false로 설정
        }

        return _deleteReviewList.value.contains(reviewId)
    }

    // isSelected 변환
    private fun changeSelectedById(reviewId: Long) {
        val review = _myReviews.value.find { it.reviewId == reviewId }
        review!!.isSelected = !review.isSelected
    }

    fun checkReviewsIsEmpty() {
        _isEmpty.value = _myReviews.value.isEmpty()
    }


    fun navigateToSetting() {
        viewModelScope.launch {
            _event.emit(MypageEvent.NavigateToSetting)
        }
    }

    fun navigateToCreateReview() {
        viewModelScope.launch {
            _event.emit(MypageEvent.NavigateToCreateReview)
        }
    }
}
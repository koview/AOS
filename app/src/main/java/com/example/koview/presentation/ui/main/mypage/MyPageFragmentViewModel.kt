package com.example.koview.presentation.ui.main.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.GlideImage
import com.example.koview.data.model.BaseState
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
}

@HiltViewModel
class MyPageFragmentViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<MypageEvent>()
    val event: SharedFlow<MypageEvent> = _event.asSharedFlow()

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    private val _profileImg = MutableStateFlow("")
    val profileImg: StateFlow<String> = _profileImg.asStateFlow()

    // 리뷰 리스트와 현재 페이지 추가
    private val _myReviews = MutableStateFlow<List<ReviewList>>(emptyList())
    val myReviews: StateFlow<List<ReviewList>> = _myReviews.asStateFlow()

    private var currentPage = 1
    private val pageSize = 10 // 페이지당 리뷰 수

    fun getMyDetail(){
        viewModelScope.launch {
            repository.getMyDetail().let {
                when (it) {
                    is BaseState.Error -> {
                        Log.d("MyPageFragment", it.code.toString() +", "+it.msg.toString())
                    }

                    is BaseState.Success -> {
                        _nickname.value = it.body.result.nickname
                        _profileImg.value = it.body.result.url
                    }
                }
            }
        }
    }

    fun getMyReviews() {
        viewModelScope.launch {
            val response = repository.getMyReviews(page = currentPage, size = pageSize)

            when (response) {
                is BaseState.Error -> {
                    Log.d("MyPageFragment", response.code.toString() + ", " + response.msg.toString())
                }
                is BaseState.Success -> {
                    // 새로운 리뷰를 현재 리스트에 추가
                    if (response.body.isSuccess) {
                        _myReviews.value = _myReviews.value + response.body.result.reviewList
                        currentPage++ // 다음 페이지로 이동
                    } else {
                        Log.d("MyPageFragment", response.body.message)
                    }
                }
            }
        }
    }


    fun navigateToSetting() {
        viewModelScope.launch {
            _event.emit(MypageEvent.NavigateToSetting)
        }
    }
}
package com.example.koview.presentation.ui.main.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.GlideImage
import com.example.koview.data.model.BaseState
import com.example.koview.data.repository.MyPageRepository
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
    private val repository: MyPageRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<MypageEvent>()
    val event: SharedFlow<MypageEvent> = _event.asSharedFlow()

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    private val _profileImg = MutableStateFlow("")
    val profileImg: StateFlow<String> = _profileImg.asStateFlow()

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


    fun navigateToSetting() {
        viewModelScope.launch {
            _event.emit(MypageEvent.NavigateToSetting)
        }
    }
}
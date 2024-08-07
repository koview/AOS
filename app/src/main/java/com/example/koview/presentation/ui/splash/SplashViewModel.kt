package com.example.koview.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.app.App.Companion.sharedPreferences
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.ReIssueRequest
import com.example.koview.data.repository.AuthRepository
import com.example.koview.presentation.utils.Constants.ACCESS_TOKEN
import com.example.koview.presentation.utils.Constants.REFRESH_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashEvent {
    data object NavigateToMainActivity : SplashEvent()
    data object NavigateToIntroActivity : SplashEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event.asSharedFlow()

    fun checkLogin() {
        viewModelScope.launch {
            val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
            val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, null)

            if (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {
                // token 정보 존재할 때 메인 화면으로 이동
                val request = ReIssueRequest(accessToken, refreshToken)
                refreshToken(request)
            } else {
                // token 정보 없을 때 로그인 화면으로 이동
                _event.emit(SplashEvent.NavigateToIntroActivity)
            }
        }
    }

    private fun refreshToken(tokenList: ReIssueRequest) {
        viewModelScope.launch {
            authRepository.refreshToken(tokenList).let {
                when (it) {
                    is BaseState.Success -> {
                        // token 저장
                        sharedPreferences.edit()
                            .putString(ACCESS_TOKEN, it.body.result.accessToken)
                            .putString(REFRESH_TOKEN, it.body.result.refreshToken)
                            .apply()

                        _event.emit(SplashEvent.NavigateToMainActivity)
                    }

                    is BaseState.Error -> {
                        // token 삭제
                        sharedPreferences.edit()
                            .remove(ACCESS_TOKEN)
                            .remove(REFRESH_TOKEN)
                            .apply()

                        _event.emit(SplashEvent.NavigateToIntroActivity)
                    }
                }
            }
        }
    }
}
package com.example.koview.presentation.ui.intro.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.app.App.Companion.sharedPreferences
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.SignInRequest
import com.example.koview.data.repository.IntroRepository
import com.example.koview.presentation.utils.Constants.ACCESS_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginEvent {
    data object NavigateToMainActivity : LoginEvent()
    data object NavigateToSignUp : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IntroRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()


    var email: MutableLiveData<String> = MutableLiveData("")

    var password: MutableLiveData<String> = MutableLiveData("")

    private var _loginCheckVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    val loginCheckVisible: LiveData<Boolean> get() = _loginCheckVisible

//    val loginchecking : MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkLogin() {

        // todo: 로그인 response accessToken 저장

        val emailValue = email.value
        val pwValue = password.value

        if (!emailValue.isNullOrBlank() && !pwValue.isNullOrBlank()) {
            viewModelScope.launch {
                val request = SignInRequest(emailValue, pwValue)
                repository.memberSignIn(request).let {
                    when (it) {
                        is BaseState.Error -> {
                            loginError()
                        }

                        is BaseState.Success -> {
                            sharedPreferences.edit()
                                .putString(ACCESS_TOKEN, it.body.result.accessToken)
                            _loginCheckVisible.value = false
                            navigateToMain()
                        }
                    }
                }
            }
        } else {
            loginError()
        }
    }

    private fun loginError() {
        _loginCheckVisible.value = true
    }

    private fun navigateToMain() {
        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToMainActivity)
        }
    }

    fun navigateToSignUp() {
        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToSignUp)
        }
    }
}
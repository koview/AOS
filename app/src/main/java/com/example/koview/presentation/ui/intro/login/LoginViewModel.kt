package com.example.koview.presentation.ui.intro.login

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()


    var email : MutableLiveData<String> = MutableLiveData("")

    var password : MutableLiveData<String> = MutableLiveData("")

    private var _loginCheckVisible : MutableLiveData<Boolean> = MutableLiveData(false)
    val loginCheckVisible : LiveData<Boolean> get() = _loginCheckVisible

    val loginchecking : MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkLogin() {
        // todo : 로그인 로직 추가
        loginApi()

        // 로그인 성공 시에 grantType, accessToken, refreshToken 받아오고 메인 액티비티로 이동
        // 실패할 시 오류 메시지 띄우고 클릭리스너 리턴
        if (loginchecking.value == true){
            _loginCheckVisible.value = false
            navigateToMain()
        } else{
            _loginCheckVisible.value = true
            email.value = ""
            password.value = ""
        }
    }

    private fun loginApi(){
        // todo : 로그인 Api연동 후 loginchecking에 response.isSuccess 넣기
        // email,password에 1을 넣었을때 로그인 성공 / 아닐때는 오류메시지 띄우기 (나중에 지워)
        loginchecking.value = email.value.equals("1") && password.value.equals("1")
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
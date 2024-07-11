package com.example.koview.presentation.ui.intro.signup.setinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SetInfoEvent {
    data object NavigateToBack : SetInfoEvent()
    data object NavigateToComplete : SetInfoEvent()
}

@HiltViewModel
class SignUpSetInfoViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SetInfoEvent>()
    val event: SharedFlow<SetInfoEvent> = _event.asSharedFlow()

    var nickname : MutableLiveData<String> = MutableLiveData("")
    var age : MutableLiveData<String> = MutableLiveData("")
    var email : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")
    var passwordCheck : MutableLiveData<String> = MutableLiveData("")

    var signUpBtnOn : MutableLiveData<Boolean> = MutableLiveData(false)
    var passwordCheckVisible : MutableLiveData<Boolean> = MutableLiveData(false)

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(SetInfoEvent.NavigateToBack)
        }
    }

    fun navigateToNext() {
        viewModelScope.launch {
            _event.emit(SetInfoEvent.NavigateToComplete)
        }
    }

    // 비밀번호 = 비밀번호 재확인 체크
    fun passwordCheck(): Boolean{
        return password.value.toString().equals(passwordCheck.value.toString())
    }

    // Edittext 값들 구독하며 validate 실행
    init {
        nickname.observeForever {
            validate()
        }
        age.observeForever {
            validate()
        }
        email.observeForever {
            validate()
        }
        password.observeForever {
            validate()
        }
        passwordCheck.observeForever {
            validate()
        }
    }


    // 빈 항목이 있는지, 이메일 형식에 맞는지, 비밀번호 확인이 맞는지 체크
    fun validate(){
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (email.value.toString().equals("")){
            return
        } else if (!email.value.toString().matches(emailPattern.toRegex())){
            return
        } else if(nickname.value.toString().equals("")){
            return
        } else if(age.value.toString().equals("")){
            return
        } else if(password.value.toString().equals("")){
            return
        } else if(!passwordCheck()){
            return
        } else {
            signUpBtnOn.value = true
            return
        }
    }

}
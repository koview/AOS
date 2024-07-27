package com.example.koview.presentation.ui.intro.signup.setinfo

import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.app.App
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.SignInRequest
import com.example.koview.data.model.requeset.SignUpRequest
import com.example.koview.data.repository.IntroRepository
import com.example.koview.presentation.utils.Constants
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
class SignUpSetInfoViewModel @Inject constructor(
    private val repository: IntroRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<SetInfoEvent>()
    val event: SharedFlow<SetInfoEvent> = _event.asSharedFlow()

    var nickname : MutableLiveData<String> = MutableLiveData("")
    var age : MutableLiveData<String> = MutableLiveData("")
    var email : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")
    var passwordCheck : MutableLiveData<String> = MutableLiveData("")

    val selectedShops = MutableLiveData<List<Int>>()

    var signUpBtnOn : MutableLiveData<Boolean> = MutableLiveData(false)
    var passwordCheckVisible : MutableLiveData<Boolean> = MutableLiveData(false)



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

    fun checkSignUp(){
        val emailValue = email.value
        val pwValue = password.value
        val nicknameValue = nickname.value
        val ageValue = age.value?.toInt()
        val shopListValue = selectedShops.value

        if (!emailValue.isNullOrBlank() && !pwValue.isNullOrBlank() && !nicknameValue.isNullOrBlank() && ageValue != null && shopListValue!!.isNotEmpty()) {
            viewModelScope.launch {
                val request = SignUpRequest(emailValue, pwValue, nicknameValue, ageValue, shopListValue)
                repository.memberSignUp(request).let {
                    when (it) {
                        is BaseState.Error -> {
                            Log.d("SignUpSetInfoFragment", "SignUp ERROR(Request Success)")
                            Log.d("SignUpSetInfoFragment", it.code.toString() +", "+ it.msg.toString())
                        }

                        is BaseState.Success -> {
                            Log.d("SignUpSetInfoFragment", "SignUp SUCCESS")
                            navigateToNext()
                        }
                    }
                }
            }
        } else {
            Log.d("SignUpSetInfoFragment", "SignUp ERROR(Request Error)")
        }
    }


    // 빈 항목이 있는지, 이메일 형식에 맞는지, 비밀번호 확인이 맞는지 체크
    fun validate(){
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (email.value.toString().equals("")){
            signUpBtnOn.value = false
            return
        } else if (!email.value.toString().matches(emailPattern.toRegex())){
            signUpBtnOn.value = false
            return
        } else if(nickname.value.toString().equals("")){
            signUpBtnOn.value = false
            return
        } else if(age.value.toString().equals("")){
            signUpBtnOn.value = false
            return
        } else if(password.value.toString().equals("")){
            signUpBtnOn.value = false
            return
        } else if(!passwordCheck()){
            signUpBtnOn.value = false
            return
        } else {
            signUpBtnOn.value = true
            return
        }
    }

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

}
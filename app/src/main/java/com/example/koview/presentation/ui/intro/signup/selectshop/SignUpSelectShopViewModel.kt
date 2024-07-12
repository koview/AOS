package com.example.koview.presentation.ui.intro.signup.selectshop

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

sealed class SelectShopEvent {
    data object NavigateToBack : SelectShopEvent()
    // 인자를 전달하기 위해 data class로 변환
    data class NavigateToSetInfo(val checkedShops: Array<String>) : SelectShopEvent()
}

@HiltViewModel
class SignUpSelectShopViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SelectShopEvent>()
    val event: SharedFlow<SelectShopEvent> = _event.asSharedFlow()

    private val _isAnyCheckboxChecked = MutableLiveData(false)
    val isAnyCheckboxChecked: LiveData<Boolean> get() = _isAnyCheckboxChecked

    private val _checkedTexts = MutableLiveData<List<String>>()
    val checkedTexts: LiveData<List<String>> get() = _checkedTexts

    private val checkboxStates = BooleanArray(5) { false }

    // 선택된 상점을 문자열로 변환하는 메서드(나중에 지워야함)
    fun getCheckedShopsString(): String {
        return _checkedTexts.value?.joinToString(", ") ?: ""
    }


    // 최종적으로 체크된 shop들을 추출
    fun updateCheckedShops(shops: List<String>) {
        _checkedTexts.value = shops
    }


    // 체크박스 중에 체크된 게 있는지 확인
    fun updateCheckboxState(index: Int, isChecked: Boolean) {
        checkboxStates[index] = isChecked
        _isAnyCheckboxChecked.value = checkboxStates.any { it }
    }




    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(SelectShopEvent.NavigateToBack)
        }
    }

    // _checkedTexts를 array로 변환하여 전달
    fun navigateToNext() {
        viewModelScope.launch {
            val checkedShops = _checkedTexts.value
            _event.emit(SelectShopEvent.NavigateToSetInfo(checkedShops!!.toTypedArray()))
        }
    }
}
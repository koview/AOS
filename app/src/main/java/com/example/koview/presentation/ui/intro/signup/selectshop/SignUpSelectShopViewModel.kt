package com.example.koview.presentation.ui.intro.signup.selectshop

import android.util.Log
import android.widget.CheckBox
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
    data class NavigateToSetInfo(val checkedShops: Array<Int>) : SelectShopEvent()
}

@HiltViewModel
class SignUpSelectShopViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SelectShopEvent>()
    val event: SharedFlow<SelectShopEvent> = _event.asSharedFlow()

    private val _isAnyCheckboxChecked = MutableLiveData(false)
    val isAnyCheckboxChecked: LiveData<Boolean> get() = _isAnyCheckboxChecked

    private val _checkedShopsId = MutableLiveData<List<Int>>()
    val checkedShopsId: LiveData<List<Int>> get() = _checkedShopsId

    private val _checkboxStates = MutableLiveData<BooleanArray>(booleanArrayOf(false, false, false, false, false))
    val checkboxStates: LiveData<BooleanArray> = _checkboxStates

    private fun getCheckedShops() {
        val checkedShops = mutableListOf<Int>()
        val checkboxStatesValue = _checkboxStates.value ?: booleanArrayOf(false, false, false, false, false)

        for (i in checkboxStatesValue.indices) {
            if (checkboxStatesValue[i]) {
                checkedShops.add(i+1)
            }
        }
        _checkedShopsId.value = checkedShops
    }

    // 체크박스 중에 체크된 게 있는지 확인 // 양방향 매핑을 위한 코드
    fun updateCheckboxState(index: Int, isChecked: Boolean) {
        val currentStates = _checkboxStates.value?.toMutableList() ?: mutableListOf(false, false, false, false, false)
        currentStates[index] = isChecked
        _checkboxStates.value = currentStates.toBooleanArray()

        _isAnyCheckboxChecked.value = hasAnyCheckedState()

    }
    //체크박스 중 하나라도 체크된게 있는지
    private fun hasAnyCheckedState(): Boolean {
        val currentStates = _checkboxStates.value ?: return false
        return currentStates.any { it }
    }




    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(SelectShopEvent.NavigateToBack)
        }
    }

    // _checkedTexts를 array로 변환하여 전달
    fun navigateToNext() {
        viewModelScope.launch {
            getCheckedShops()
            Log.d("SignUpSelectShopFragment", "Selected Shops id: ${_checkedShopsId.value.toString()}")
            val checkedShops = _checkedShopsId.value
            _event.emit(SelectShopEvent.NavigateToSetInfo(checkedShops!!.toTypedArray()))
        }
    }
}
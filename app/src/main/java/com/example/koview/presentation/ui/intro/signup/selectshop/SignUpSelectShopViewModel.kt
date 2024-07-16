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

    private val _checkboxStates = MutableLiveData<BooleanArray>(booleanArrayOf(false, false, false, false, false))
    val checkboxStates: LiveData<BooleanArray> = _checkboxStates

    val checkedShops: ArrayList<String> = arrayListOf("Amazon", "eBay", "AliExpress", "Walmart", "Target")

    // 선택된 상점을 문자열로 변환하는 메서드(나중에 지워야함)
    private fun getCheckedShopsString(): String {
        return _checkedTexts.value?.joinToString(", ") ?: ""
    }

    private fun getCheckedShops() {
        val checkedShops = mutableListOf<String>()
        val checkboxStatesValue = _checkboxStates.value ?: booleanArrayOf(false, false, false, false, false)

        for (i in checkboxStatesValue.indices) {
            if (checkboxStatesValue[i]) {
                checkedShops.add(this.checkedShops[i])
            }
        }
        _checkedTexts.value = checkedShops
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
            Log.d("SignUpSelectShopFragment", "Selected Shops String: ${getCheckedShopsString()}")
            val checkedShops = _checkedTexts.value
            _event.emit(SelectShopEvent.NavigateToSetInfo(checkedShops!!.toTypedArray()))
        }
    }
}
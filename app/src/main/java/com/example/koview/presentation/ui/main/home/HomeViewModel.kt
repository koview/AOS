package com.example.koview.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.presentation.ui.main.home.model.AskUiData
import com.example.koview.presentation.ui.main.home.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeEvent {
    data object ShowCategoryBottomSheet : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    private val _category = MutableStateFlow(Category.ALL)
    val category: StateFlow<Category> = _category.asStateFlow()

    private val _askList = MutableStateFlow<List<AskUiData>>(emptyList())
    val askList: StateFlow<List<AskUiData>> = _askList.asStateFlow()


    init {
        setAskListData()
    }

    private fun setAskListData() {
        viewModelScope.launch {
            // todo : 질문 데이터 가져오기
            val fetchedData = listOf(
                AskUiData(
                    "이 목걸이 안전할까요? 실버라 조금 불안하네요.",
                    50,
                    30,
                    "https://ae01.alicdn.com/kf/Sb638d30edc6f47138b73e40d3e3dd765w/925.jpg_.webp"
                ),
                AskUiData(
                    "하트 눈사람 집게 찾습니다! 이 제품 어디서 파는지 아시는분",
                    35,
                    4,
                    "https://img.kwcdn.com/product/Fancyalgo/VirtualModelMatting/4a39ca73b600f57ed33b50df574c5f06.jpg?imageView2/2/w/800/q/70/format/webp"
                ),
                AskUiData(
                    "침착맨이 테무깡에서 신은 금색 신발이 뭔지 궁금합니다. 친구 선물로 줄 예정입니다",
                    103,
                    25,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTt8BHAabSNHYgMLPjbnuWogrYJbjQp86C3cg&s"
                )
            )
            _askList.value = fetchedData
        }
    }

    fun showCategoryBottomSheet() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowCategoryBottomSheet)
        }
    }

    fun applyFilter(filter: Category) {
        viewModelScope.launch {
            // todo : 해당 카테고리의 상품들로 정보 다시 불러오기
            _category.value = filter
        }
    }
}
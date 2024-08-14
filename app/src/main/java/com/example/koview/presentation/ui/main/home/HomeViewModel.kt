package com.example.koview.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.repository.MainRepository
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
    data object NavigateToHarmfulProduct : HomeEvent()
    data object NavigateToPopularProduct : HomeEvent()
    data object NavigateToSearch : HomeEvent()
    data object NavigateToAsk : HomeEvent()
    data class ShowToastMessage(val msg: String) : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    private val _category = MutableStateFlow(Category.ALL)
    val category: StateFlow<Category> = _category.asStateFlow()

    private val _queryList = MutableStateFlow<List<AskUiData>>(emptyList())
    val queryList: StateFlow<List<AskUiData>> = _queryList.asStateFlow()

    private val _harmfulProdImage = MutableStateFlow<List<String>>(emptyList())
    val harmfulProdImage: StateFlow<List<String>> = _harmfulProdImage.asStateFlow()

    private val _popularProdImage = MutableStateFlow<List<String>>(emptyList())
    val popularProdImage: StateFlow<List<String>> = _popularProdImage.asStateFlow()

    init {
        setProductImage()
    }

    private fun setProductImage() {
        viewModelScope.launch {
            repository.home().let { it ->
                when (it) {
                    is BaseState.Success -> {
                        val famousProducts = it.body.result.famousFourProducts
                        val restrictedProducts = it.body.result.restrictedFourProducts
                        val fourQueriesList = it.body.result.lastFourQueries

                        // 유해 상품
                        _harmfulProdImage.value = restrictedProducts.map { it.image.url }
                        // 인기 상품
                        _popularProdImage.value = famousProducts.map { it.image.url }
                        // 최근 질문
                        _queryList.value = fourQueriesList.map { query ->
                            AskUiData(
                                content = query.content,
                                viewCount = query.totalViewCount,
                                answerCount = query.totalAnswerCount,
                                withQueryCount = query.totalWithQueryCount,
                                isWithQuery = query.isWithQuery,
                                askImage = query.imageList?.firstOrNull()?.url
                                    ?: ""  // 첫 번째 이미지 사용, 없으면 빈 문자열
                            )
                        }
                    }

                    is BaseState.Error -> {
                        _event.emit(HomeEvent.ShowToastMessage(it.msg))
                    }
                }
            }
        }
    }

    fun showCategoryBottomSheet() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowCategoryBottomSheet)
        }
    }

    fun applyFilter(filter: Category) {
        viewModelScope.launch {
            _category.value = filter
        }
    }

    fun navigateToHarmfulProduct() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToHarmfulProduct)
        }
    }

    fun navigateToPopularProduct() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToPopularProduct)
        }
    }

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToSearch)
        }
    }

    fun navigateToAsk() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToAsk)
        }
    }
}
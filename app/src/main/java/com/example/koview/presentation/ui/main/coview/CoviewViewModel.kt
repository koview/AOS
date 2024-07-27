package com.example.koview.presentation.ui.main.coview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.repository.MainRepository
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoviewViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _reviewList = MutableStateFlow<List<CoviewUiData>>(emptyList())
    val reviewList: StateFlow<List<CoviewUiData>> = _reviewList.asStateFlow()

    init {
        setReviewDate()
    }

    private fun setReviewDate() {
        // todo: 리뷰 리스트 api 연결

        val reviewList = listOf(
            CoviewUiData(
                "네로",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQN8Mp1QGqi5jpAw5RQkbpKPby0-dKs7SjtGA&s",
                23,
                3,
                true,
                "코뷰 화이팅 입니다~!\n개발을 하자 네로야.",
                "2024.07.27"
            ),
            CoviewUiData(
                "네로",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQN8Mp1QGqi5jpAw5RQkbpKPby0-dKs7SjtGA&s",
                43,
                22,
                false,
                "제품 리뷰 내용 부분입니다.\n내용이 2줄을 넘으면 잘려 보여지는데 마지막에 ...이 붙어 보여집니다. 잘 보여지고 있나요?",
                "2024.07.25"
            ),
            CoviewUiData(
                "멜리",
                "https://cdn.wadiz.kr/ft/images/green001/2021/1213/20211213120822340_4.jpg/wadiz/format/jpg/quality/80/",
                43,
                22,
                true,
                "맬리 최고야",
                "2024.07.25"
            ),
            CoviewUiData(
                "커너",
                "https://avatars.githubusercontent.com/u/98101954?v=4",
                50,
                4,
                false,
                "요즘 갓생 사시는 이분",
                "2024.07.25"
            )
        )

        _reviewList.update { reviewList }
    }

    fun onLikeClick(item: CoviewUiData) {
        viewModelScope.launch {
            // todo: 좋아요 업데이트 api 호출
            val updatedItem = item.copy(likeState = !item.likeState)

            _reviewList.update { list ->
                list.map {
                    if (it == item) updatedItem else it
                }
            }
        }
    }
}
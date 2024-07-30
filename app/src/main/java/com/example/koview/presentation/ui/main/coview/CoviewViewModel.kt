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
                "2024.07.27",
                listOf(
                    "https://img.kwcdn.com/product/Fancyalgo/VirtualModelMatting/c726d7f77c97f1e4aeac2f504cc5a2e6.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/53a70e24-b299-4870-a9dc-0699e04b5645.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/Fancyalgo/VirtualModelMatting/3e14021aca9552b26904157e51950d23.jpg?imageView2/2/w/800/q/70/format/webp"
                )
            ),
            CoviewUiData(
                "네로",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQN8Mp1QGqi5jpAw5RQkbpKPby0-dKs7SjtGA&s",
                43,
                22,
                false,
                "제품 리뷰 내용 부분입니다.\n내용이 2줄을 넘으면 잘려 보여지는데 마지막에 ...이 붙어 보여집니다. 잘 보여지고 있나요?",
                "2024.07.25",
                listOf(
                    "https://img.kwcdn.com/product/fancy/ace1f3c2-23eb-482c-8abc-a530984a4c53.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/4ffbfb43-b157-4b00-8e71-71c59a0e7d19.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/3920e8d3-34c6-4ec9-98f0-3aecffb92b5e.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/fb538041-60dd-4e21-b734-57aba895d904.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/8db8ec81-2650-433c-83c1-882da1e10ec0.jpg?imageView2/2/w/800/q/70/format/webp"
                )
            ),
            CoviewUiData(
                "멜리",
                "https://cdn.wadiz.kr/ft/images/green001/2021/1213/20211213120822340_4.jpg/wadiz/format/jpg/quality/80/",
                43,
                22,
                true,
                "맬리 최고야",
                "2024.07.25",
                listOf(
                    "https://img.kwcdn.com/product/fancy/5efdc117-a105-4497-b871-a7f2d6f744d0.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/d2ebf821-00da-48b7-82d7-28f43104b9a4.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/6c9d7eb6-6fde-47c0-9eaf-dc86336e9d91.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/9f7b28d1-dd38-48ea-98b1-dfbab260bfc4.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/c457753a-a3ab-4cb2-b3ce-085c91e84688.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/e9ac5ca1-f669-4aa2-a323-79923128c8ec.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/a2dc6a3d-4559-4048-8faa-dbd6f9b01355.jpg?imageView2/2/w/800/q/70/format/webp"
                )
            ),
            CoviewUiData(
                "커너",
                "https://avatars.githubusercontent.com/u/98101954?v=4",
                50,
                4,
                false,
                "요즘 갓생 사시는 이분",
                "2024.07.25",
                listOf(
                    "https://img.kwcdn.com/product/fancy/fcb2019c-528a-4fd5-9600-0cee8d618836.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/5b0e02e2-3027-4670-bfe8-5d6813f8fd0e.jpg?imageView2/2/w/800/q/70/format/webp",
                    "https://img.kwcdn.com/product/fancy/28590b27-4817-4563-8a29-5c925b5c7825.jpg?imageView2/2/w/800/q/70/format/webp"
                )
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
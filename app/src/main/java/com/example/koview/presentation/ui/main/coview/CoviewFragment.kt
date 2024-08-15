package com.example.koview.presentation.ui.main.coview

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentCoviewBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.coview.adapter.CoviewClickListener
import com.example.koview.presentation.ui.main.coview.adapter.CoviewReviewAdapter
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
import com.example.koview.presentation.ui.main.global.model.ReviewType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoviewFragment : BaseFragment<FragmentCoviewBinding>(R.layout.fragment_coview),
    CoviewClickListener {

    private val viewModel: CoviewViewModel by activityViewModels()
    private var adapter: CoviewReviewAdapter? = null

    private var bottomScrollState = true

    // 사용자가 링크를 통해 나갔다가 돌아왔는지 여부
    private var isReturningFromExternal = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserver()
        initStateObserver()
        initAdapter()
        addOnScrollListener()
    }

    override fun onResume() {
        super.onResume()

        if (!isReturningFromExternal) {
            viewModel.getUserInfo()
        }
        isReturningFromExternal = false
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.resetKeyword()
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is CoviewEvent.ShowToastMessage -> showToastMessage(it.msg)
                    CoviewEvent.ShowLoading -> showLoading(requireContext())
                    CoviewEvent.DismissLoading -> dismissLoading()
                }
            }
        }
    }

    // 리뷰 데이터 설정
    private fun initStateObserver() {
        repeatOnStarted {
            viewModel.uiState.collect {
                adapter?.submitList(it.reviewList)
            }
        }
    }

    private fun initAdapter() {
        adapter = CoviewReviewAdapter(this)
        binding.rvCoviewList.adapter = adapter
    }

    // 무한 스크롤
    private fun addOnScrollListener() {
        binding.layoutScroll.setOnScrollChangeListener { v, _, scrollY, _, _ ->

            if (scrollY > binding.layoutScroll.getChildAt(0).measuredHeight - v.measuredHeight) {
                // 화면 하단에 도달
                if (bottomScrollState) {
                    bottomScrollState = false

                    // 검색 모드에 따라 다른 함수 호출
                    if (viewModel.isSearchMode.value) {
                        viewModel.searchReviews()
                        checkMode()
                        Log.d("코뷰", "리뷰 검색")
                    } else {
                        viewModel.getReviews()
                        Log.d("코뷰", "리뷰 전체")
                    }
                }
            } else {
                bottomScrollState = true
            }
        }
    }

    private fun checkMode() {
        viewModel.checkSearchMode()
    }

    // 리뷰 아이템 좋아요 클릭 시 호출
    override fun onLikeClick(item: CoviewUiData) {
        viewModel.onLikeClick(item)
    }

    // 상품 링크 클릭 시 호출
    override fun onShopTagClick(url: String) {
        isReturningFromExternal = true
        clickTag(url)
    }

    // 댓글 아이콘 클릭 시 호출
    override fun onCommentClick(reviewId: Long, isFullView: Boolean) {
        findNavController().toComment(reviewId, viewModel.profileImgUrl ?: "", isFullView)
    }

    private fun clickTag(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun NavController.toComment(reviewId: Long, profileUrl: String, isFullView: Boolean) {
        Log.d("코뷰", "리뷰 아이디: $reviewId")
        val action =
            CoviewFragmentDirections.actionCoviewFragmentToCoviewCommentBottomSheetFragment(
                reviewId,
                profileUrl,
                isFullView,
                ReviewType.COVIEW.toString()
            )
        navigate(action)
    }
}
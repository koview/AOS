package com.example.koview.presentation.ui.main.global.reviewdetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.koview.R
import com.example.koview.databinding.FragmentUserReviewDetailBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.coview.adapter.CoviewClickListener
import com.example.koview.presentation.ui.main.coview.adapter.CoviewReviewAdapter
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
import com.example.koview.presentation.ui.main.global.model.ReviewType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserReviewDetailFragment :
    BaseFragment<FragmentUserReviewDetailBinding>(R.layout.fragment_user_review_detail),
    CoviewClickListener {

    val viewModel: UserReviewDetailViewModel by activityViewModels()

    private val args: UserReviewDetailFragmentArgs by navArgs()
    private val reviewId by lazy { args.reviewId }
    private val nickname by lazy { args.nickname }


    private var adapter: CoviewReviewAdapter? = null

    // 사용자가 링크를 통해 나갔다가 돌아왔는지 여부
    private var isReturningFromExternal = false
    private var bottomScrollState = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setNickname(nickname)

        initEventObserver()
        initAdapter()
        initStateObserver()
        addOnScrollListener()
    }

    override fun onResume() {
        super.onResume()

        if (!isReturningFromExternal) {
            viewModel.getUserInfo(reviewId)
        }
        isReturningFromExternal = false
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    UserReviewDetailEvent.DismissLoading -> dismissLoading()
                    UserReviewDetailEvent.NavigateToBack -> findNavController().toBack()
                    UserReviewDetailEvent.ShowLoading -> showLoading(requireContext())
                    is UserReviewDetailEvent.ShowToastMessage -> showToastMessage(it.msg)
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
        binding.rvReviewList.adapter = adapter
    }

    // 무한 스크롤
    private fun addOnScrollListener() {
        binding.layoutScroll.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY > binding.layoutScroll.getChildAt(0).measuredHeight - v.measuredHeight) {
                // 화면 하단에 도달
                if (bottomScrollState) {
                    bottomScrollState = false

                    viewModel.getReviews(reviewId)
                }
            } else {
                bottomScrollState = true
            }
        }
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
        val action =
            UserReviewDetailFragmentDirections.actionUserReviewDetailFragmentToCoviewCommentBottomSheetFragment(
                reviewId,
                profileUrl,
                isFullView,
                ReviewType.REVIEW_DETAIL.toString()
            )
        navigate(action)
    }

    private fun NavController.toBack() {
        findNavController().navigateUp()
    }

}
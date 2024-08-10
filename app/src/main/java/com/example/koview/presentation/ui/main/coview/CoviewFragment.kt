package com.example.koview.presentation.ui.main.coview

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import com.example.koview.R
import com.example.koview.databinding.FragmentCoviewBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.coview.adapter.CoviewClickListener
import com.example.koview.presentation.ui.main.coview.adapter.CoviewReviewAdapter
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoviewFragment : BaseFragment<FragmentCoviewBinding>(R.layout.fragment_coview),
    CoviewClickListener {

    private val viewModel: CoviewViewModel by viewModels()
    private var adapter: CoviewReviewAdapter? = null

    private var bottomScrollState = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        viewModel.getUserInfo()

        initEventObserver()
        initStateObserver()
        initAdapter()
        addOnScrollListener()
    }

    override fun onPause() {
        super.onPause()

        viewModel.getUserInfo()
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
                adapter?.setList(it.reviewList)
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
                    viewModel.getReviews()
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
        clickTag(url)
    }

    private fun clickTag(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }
}
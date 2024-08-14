package com.example.koview.presentation.ui.main.ask.askdetail

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.data.model.response.QueryAnswerList
import com.example.koview.databinding.FragmentAskDetailBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.ask.AskViewModel
import com.example.koview.presentation.ui.main.ask.askdetail.adapter.AskAnswerAdapter
import com.example.koview.presentation.ui.main.ask.askdetail.adapter.AskShopTagAdapter
import com.example.koview.presentation.ui.main.global.product.model.Review
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class AskDetailFragment : BaseFragment<FragmentAskDetailBinding>(R.layout.fragment_ask_detail),
    AskDetailInterface {

    private val viewModel: AskDetailViewModel by activityViewModels()
    private val parentViewModel: AskViewModel by activityViewModels()
    private val askAnswerAdapter = AskAnswerAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        // 데이터 관찰
        parentViewModel.askDetail.observe(viewLifecycleOwner) { askDetail ->
            binding.model = askDetail
        }

        initRecyclerview()
        initEventObserve()
        initAnswerListObserver()
        clickAsk()
    }

    private fun initRecyclerview() {
        // 쇼핑몰 태그 리사이클러뷰 연결
        // FlexboxLayoutManager 설정
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = com.google.android.flexbox.FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.FLEX_START

        binding.rvShop.layoutManager = layoutManager
        binding.rvShop.adapter =
            parentViewModel.askDetail.value?.let { AskShopTagAdapter(this, it.purchaseLinkList) }

        // 답변 리사이클러뷰 연결
        binding.rvReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = askAnswerAdapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    AskDetailEvent.NavigateToAsk -> findNavController().toAsk()
                    AskDetailEvent.NavigateToAskAnswer -> findNavController().toAskAnswer()
                }
            }
        }
    }

    private fun initAnswerListObserver() {
        repeatOnStarted {
            viewModel.getAnswers.collect {
                askAnswerAdapter.updateReviews(it)
            }
        }
        parentViewModel.askDetail.value?.let { viewModel.getQueryAnswers(it.queryId) }
    }

    private fun NavController.toAsk() {
        val action = AskDetailFragmentDirections.actionAskDetailFragmentToAskFragment()
        navigate(action)
    }

    private fun NavController.toAskAnswer() {
        val action = AskDetailFragmentDirections.actionAskDetailFragmentToAskAnswerFragment()
        navigate(action)
    }

    private fun clickTag(url: String?) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun clickAsk() {
        binding.layoutAskIcon.setOnClickListener {
//            parentViewModel.askDetail.value?.let { parentViewModel.onAskClick(it) }
        }
    }

    override fun onClickTag(url: String) {
        clickTag(url)
    }

    override fun onClickLike(item: QueryAnswerList) {
//        parentViewModel.onLikeClick(item)
    }
}
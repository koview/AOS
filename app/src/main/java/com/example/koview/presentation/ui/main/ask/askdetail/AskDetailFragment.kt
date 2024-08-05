package com.example.koview.presentation.ui.main.ask.askdetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentAskDetailBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.ask.AskViewModel
import com.example.koview.presentation.ui.main.ask.askdetail.adapter.AskShopTagAdapter
import com.example.koview.presentation.ui.main.global.product.adapter.ProductShopTagAdapter
import com.example.koview.presentation.ui.main.global.productdetail.adapter.ProductReviewAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class AskDetailFragment: BaseFragment<FragmentAskDetailBinding>(R.layout.fragment_ask_detail), AskDetailInterface {

    private val viewModel: AskDetailViewModel by viewModels()
    private val parentViewModel: AskViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.model = parentViewModel.askDetail.value
        binding.vm = viewModel

        initRecyclerview()
    }

    private fun initRecyclerview() {
        // 쇼핑몰 태그 리사이클러뷰 연결
        // FlexboxLayoutManager 설정
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = com.google.android.flexbox.FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.FLEX_START

        binding.rvShop.layoutManager = layoutManager
        binding.rvShop.adapter = parentViewModel.askDetail.value?.let { AskShopTagAdapter(this, it.shopList) }

//        // 리뷰 리사이클러뷰 연결
//        binding.rvReview.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvReview.adapter =
//            productViewModel.searchProduct.value?.let {
//                ProductReviewAdapter(
//                    it.reviewList
//                )
//            }
    }

    private fun clickTag(url: String?) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    override fun onClickTag(url: String) {
        clickTag(url)
    }
}
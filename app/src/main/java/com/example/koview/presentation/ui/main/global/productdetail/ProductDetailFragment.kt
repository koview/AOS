package com.example.koview.presentation.ui.main.global.productdetail

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
import com.example.koview.databinding.FragmentProductDetailBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.ProductEvent
import com.example.koview.presentation.ui.main.global.ProductViewModel
import com.example.koview.presentation.ui.main.global.productdetail.adapter.ProductReviewAdapter
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.example.koview.presentation.ui.main.global.product.adapter.ProductShopTagAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {

    private val parentViewModel: SearchViewModel by activityViewModels()
    private val viewModel: ProductDetailViewModel by viewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.model = productViewModel.searchProduct.value

        initRecyclerview()
        initEventObserve()
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
            productViewModel.searchProduct.value?.let {
                ProductShopTagAdapter(
                    productViewModel,
                    it.shopList
                )
            }

        // 리뷰 리사이클러뷰 연결
        binding.rvReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter =
            productViewModel.searchProduct.value?.let {
                ProductReviewAdapter(
                    parentViewModel,
                    it.reviewList
                )
            }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    ProductDetailEvent.NavigateToSearch -> findNavController().toSearch()
                }
            }
        }
        repeatOnStarted {
            productViewModel.event.collect() {
                when (it) {
                    is ProductEvent.ClickTag -> clickTag(productViewModel.searchProductUrl.value)
                    else -> {}
                }
            }
        }
    }

    private fun NavController.toSearch() {
        val action = ProductDetailFragmentDirections.actionProductDetailFragmentToSearchFragment()
        navigate(action)
    }

    private fun clickTag(url: String?) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }


}
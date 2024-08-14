package com.example.koview.presentation.ui.main.global.productdetail

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.data.model.response.ProductReviewDetail
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.databinding.FragmentProductDetailBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.product.ProductInterface
import com.example.koview.presentation.ui.main.global.product.ProductViewModel
import com.example.koview.presentation.ui.main.global.product.adapter.ProductShopTagAdapter
import com.example.koview.presentation.ui.main.global.productdetail.adapter.ProductReviewAdapter
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail), ProductInterface,
    ProductDetailInterface {

    private val parentViewModel: SearchViewModel by activityViewModels()
    private val viewModel: ProductDetailViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private val productReviewAdapter = ProductReviewAdapter(this)

    private var sourceFragmentId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.model = productViewModel.searchProduct.value
        // productImageUrl이 없으면 빈 url
        if (productViewModel.searchProduct.value?.productImageUrls.isNullOrEmpty()) {
            binding.imageUrl = ""
        } else { // 있으면 첫 번째에 해당하는 url
            val imageUrl =
                productViewModel.searchProduct.value?.productImageUrls?.firstOrNull()?.url ?: ""
            binding.imageUrl = imageUrl
        }

        val args: ProductDetailFragmentArgs by navArgs()
        sourceFragmentId = args.sourceFragmentId

        handleBackNavigation()

        initRecyclerview()
        initEventObserve()
        initProductListObserver()
    }

    // ProductDetailViewModel Review data
    private fun initProductListObserver() {
        repeatOnStarted {
            viewModel.getReviews.collect {
                productReviewAdapter.submitList(it)
            }
        }
        productViewModel.searchProduct.value?.let { viewModel.getReviewDetails(it.productId) }
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
                    this,
                    it.purchaseLinkList
                )
            }

        // 리뷰 리사이클러뷰 연결
        binding.rvReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = productReviewAdapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    ProductDetailEvent.NavigateToSearch -> findNavController().toSearch()
                }
            }
        }
    }

    // 뒤로가기 버튼 동작 처리
    private fun handleBackNavigation() {
        binding.btnBack.setOnClickListener {
            when (sourceFragmentId) {
                R.id.search_fragment -> findNavController().popBackStack(
                    R.id.search_fragment,
                    false
                )

                R.id.harmful_product_fragment -> findNavController().popBackStack(
                    R.id.harmful_product_fragment,
                    false
                )

                R.id.popular_product_fragment -> findNavController().popBackStack(
                    R.id.popular_product_fragment,
                    false
                )

                else -> findNavController().popBackStack()
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

    override fun onProductClick(product: SingleProduct) {
        // 사용 X
    }

    override fun onProductShopTagClick(url: String) {
        clickTag(url)
    }

    override fun onLikeClick(item: ProductReviewDetail) {
        viewModel.onLikeClick(item)
    }

}
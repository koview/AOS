package com.example.koview.presentation.ui.main.home.search.productdetail

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.koview.R
import com.example.koview.databinding.FragmentProductDetailBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.example.koview.presentation.ui.main.home.search.adapter.SearchShopAdapter
import com.example.koview.presentation.ui.main.home.search.productdetail.adapter.ProductReviewAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.collect

class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {

    private val parentViewModel: SearchViewModel by activityViewModels()
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.model = parentViewModel.searchProduct.value

        initRecyclerview()
        setProductImage()
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
            parentViewModel.searchProduct.value?.let { SearchShopAdapter(it.shopList) }

        // 리뷰 리사이클러뷰 연결
        binding.rvReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter =
            parentViewModel.searchProduct.value?.let { ProductReviewAdapter(parentViewModel, it.reviewList) }
    }

    private fun setProductImage() {
        val productImageUrl = parentViewModel.searchProduct.value?.imageUrl
        binding.ivProduct.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    binding.ivProduct.viewTreeObserver.removeOnPreDrawListener(this)
                    val width = binding.ivProduct.width
                    val height = binding.ivProduct.height

                    val requestOptions =
                        RequestOptions().transform(CenterCrop(), RoundedCorners(16))

                    Glide.with(requireContext())
                        .load(productImageUrl)
                        .placeholder(R.drawable.default_product_image)
                        .error(R.drawable.default_product_image)
                        .apply(requestOptions)
                        .override(width, height)
                        .into(binding.ivProduct)

                    return true
                }
            }
        )
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when(it) {
                    ProductDetailEvent.NavigateToSearch -> findNavController().toSearch()
                }
            }
        }
    }

    private fun NavController.toSearch() {
        val action = ProductDetailFragmentDirections.actionProductDetailFragmentToSearchFragment()
        navigate(action)
    }


}
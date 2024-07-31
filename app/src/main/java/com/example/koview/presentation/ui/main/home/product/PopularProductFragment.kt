package com.example.koview.presentation.ui.main.home.product

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentPopularProductBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.product.ProductEvent
import com.example.koview.presentation.ui.main.global.product.ProductViewModel
import com.example.koview.presentation.ui.main.home.HomeEvent
import com.example.koview.presentation.ui.main.home.HomeViewModel
import com.example.koview.presentation.ui.main.global.product.adapter.ProductAdapter
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct

class PopularProductFragment :
    BaseFragment<FragmentPopularProductBinding>(R.layout.fragment_popular_product) {

    private val viewModel: PopularProductViewModel by activityViewModels()
    private val parentViewModel: HomeViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.parentVm = parentViewModel

        productAdapter = ProductAdapter(productViewModel)

        initPopularProductRecyclerview()
        initPopularProductListObserver()
        initEventObserve()
    }

    private fun initPopularProductRecyclerview() {
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = productAdapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            parentViewModel.event.collect() {
                when (it) {
                    HomeEvent.ShowCategoryBottomSheet -> findNavController().toCategoryBottomSheet()
                    else -> {}
                }
            }
        }
        repeatOnStarted {
            productViewModel.event.collect { event ->
                when (event) {
                    is ProductEvent.NavigateToProductDetail -> {
                        productViewModel.searchProduct.value?.let { searchProduct ->
                            findNavController().toProductDetail(searchProduct)
                        }
                    }

                    is ProductEvent.ClickTag -> clickTag(productViewModel.searchProductUrl.value)
                }
            }
        }
    }

    private fun initPopularProductListObserver() {
        repeatOnStarted {
            viewModel.popularProductList.collect { popularProductList ->
                productAdapter.submitList(popularProductList)
            }
        }
    }

    private fun NavController.toCategoryBottomSheet() {
        val action =
            PopularProductFragmentDirections.actionPopularProductFragmentToHomeCategorySelectFragment()
        navigate(action)
    }

    private fun NavController.toProductDetail(searchProduct: SearchProduct) {
        val action =
            PopularProductFragmentDirections.actionPopularProductFragmentToProductDetailFragment(
                searchProduct
            )
        navigate(action)
    }

    private fun clickTag(url: String?) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }
}
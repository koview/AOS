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
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.databinding.FragmentHarmfulProductBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.product.ProductEvent
import com.example.koview.presentation.ui.main.global.product.ProductInterface
import com.example.koview.presentation.ui.main.global.product.ProductViewModel
import com.example.koview.presentation.ui.main.global.product.adapter.ProductAdapter
import com.example.koview.presentation.ui.main.global.product.model.Product
import com.example.koview.presentation.ui.main.home.HomeEvent
import com.example.koview.presentation.ui.main.home.HomeViewModel

class HarmfulProductFragment :
    BaseFragment<FragmentHarmfulProductBinding>(R.layout.fragment_harmful_product),
    ProductInterface {

    private val viewModel: HarmfulProductViewModel by activityViewModels()
    private val parentViewModel: HomeViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.parentVm = parentViewModel

        productAdapter = ProductAdapter(this)

        initHarmfulProductRecyclerview()
        initHarmfulProductListObserver()
        initEventObserve()
    }

    private fun initHarmfulProductRecyclerview() {
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = productAdapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            parentViewModel.event.collect() {
                when (it) {
                    // 카테고리 선택 바텀시트
                    HomeEvent.ShowCategoryBottomSheet -> findNavController().toCategoryBottomSheet()
                    else -> {}
                }
            }
        }
        repeatOnStarted {
            productViewModel.event.collect { event ->
                when (event) {
                    // ProductDetail 화면으로 데이터를 같이 넘김
                    is ProductEvent.NavigateToProductDetail -> {
                        productViewModel.searchProduct.value?.let { searchProduct ->
                            findNavController().toProductDetail(searchProduct)
                        }
                    }

                }
            }
        }
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    // Home 화면으로 이동
                    HarmfulProductEvent.NavigateToHome -> findNavController().toHome()
                }
            }
        }
    }

    private fun initHarmfulProductListObserver() {
        repeatOnStarted {
            viewModel.getProducts.collect { harmfulProductList ->
                productAdapter.submitList(harmfulProductList)
            }
        }
    }

    private fun NavController.toCategoryBottomSheet() {
        val action =
            HarmfulProductFragmentDirections.actionHarmfulProductFragmentToHomeCategorySelectFragment()
        navigate(action)
    }

    private fun NavController.toProductDetail(harmfulProduct: SingleProduct) {
        val action =
            HarmfulProductFragmentDirections.actionHarmfulProductFragmentToProductDetailFragment(
                harmfulProduct
            )
        navigate(action)
    }

    private fun NavController.toHome() {
        val action = HarmfulProductFragmentDirections.actionHarmfulFragmentToHomeFragment()
        navigate(action)
    }

    private fun clickTag(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }


    override fun onProductClick(product: SingleProduct) {
        productViewModel.navigateToProductDetail(product)
    }

    override fun onProductShopTagClick(url: String) {
        clickTag(url)
    }
}
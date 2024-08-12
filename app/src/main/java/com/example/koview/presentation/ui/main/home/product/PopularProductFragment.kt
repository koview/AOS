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
import com.example.koview.databinding.FragmentPopularProductBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.product.ProductEvent
import com.example.koview.presentation.ui.main.global.product.ProductInterface
import com.example.koview.presentation.ui.main.global.product.ProductViewModel
import com.example.koview.presentation.ui.main.global.product.adapter.ProductAdapter
import com.example.koview.presentation.ui.main.global.product.model.Product
import com.example.koview.presentation.ui.main.home.HomeEvent
import com.example.koview.presentation.ui.main.home.HomeViewModel

class PopularProductFragment :
    BaseFragment<FragmentPopularProductBinding>(R.layout.fragment_popular_product),
    ProductInterface {

    private val viewModel: PopularProductViewModel by activityViewModels()
    private val parentViewModel: HomeViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.parentVm = parentViewModel

        productAdapter = ProductAdapter(this)

        initPopularProductRecyclerview()
        initPopularProductListObserver()
        initEventObserve()
        initProductData()
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
                    PopularProductEvent.NavigateToHome -> findNavController().toHome()
                }
            }
        }
    }

    private fun initPopularProductListObserver() {
        repeatOnStarted {
            viewModel.getProducts.collect { popularProductList ->
                productAdapter.submitList(popularProductList)
            }
        }
    }

    // 다른 화면으로 갔다가 돌아 왔을 때 초기화
    private fun initProductData() {
        repeatOnStarted {
            viewModel.getProducts(category = parentViewModel.category.value)
        }
    }

    private fun NavController.toCategoryBottomSheet() {
        val action =
            PopularProductFragmentDirections.actionPopularProductFragmentToHomeCategorySelectFragment()
        navigate(action)
    }

    private fun NavController.toProductDetail(popularProduct: SingleProduct) {
        val action =
            PopularProductFragmentDirections.actionPopularProductFragmentToProductDetailFragment(
                popularProduct
            )
        navigate(action)
    }

    private fun NavController.toHome() {
        val action = PopularProductFragmentDirections.actionPopularFragmentToHomeFragment()
        navigate(action)
    }

    private fun clickTag(url: String?) {
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
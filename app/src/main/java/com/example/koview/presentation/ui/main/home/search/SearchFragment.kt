package com.example.koview.presentation.ui.main.home.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.data.model.response.ProductsResult
import com.example.koview.data.model.response.SingleProduct
import com.example.koview.databinding.FragmentSearchBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.product.ProductEvent
import com.example.koview.presentation.ui.main.global.product.ProductInterface
import com.example.koview.presentation.ui.main.global.product.ProductViewModel
import com.example.koview.presentation.ui.main.global.product.adapter.ProductAdapter
import com.example.koview.presentation.ui.main.global.product.model.Product
import com.example.koview.presentation.ui.main.home.HomeEvent
import com.example.koview.presentation.ui.main.home.HomeViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search), ProductInterface {

    private val viewModel: SearchViewModel by activityViewModels()
    private val parentViewModel: HomeViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.parentVm = parentViewModel

        productAdapter = ProductAdapter(this)

        initSearchProductRecyclerview()
        initEventObserve()
        initProductListObserver()
        initSearchTerm()
        enterSearch()
    }

    private fun initSearchProductRecyclerview() {
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = productAdapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            productViewModel.event.collect { event ->
                when (event) {
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
                    SearchEvent.NavigateToHome -> findNavController().toHome()
                }
            }
        }
        repeatOnStarted {
            parentViewModel.event.collect() {
                when (it) {
                    HomeEvent.ShowCategoryBottomSheet -> findNavController().toCategoryBottomSheet()
                    else -> {}
                }
            }
        }
    }

    // SearchViewModel data
    private fun initProductListObserver() {
        repeatOnStarted {
            viewModel.getProducts.collect {singleProductList ->
                productAdapter.submitList(singleProductList)
            }
        }
    }

    // 다른 화면으로 갔다가 돌아 왔을 때 검색어 초기화
    private fun initSearchTerm() {
        repeatOnStarted {
            viewModel.getProducts(searchTerm = null, category = parentViewModel.category.value)
        }
    }

    private fun NavController.toProductDetail(searchProduct: SingleProduct) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(searchProduct)
        navigate(action)
    }

    private fun NavController.toHome() {
        val action = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
        navigate(action)
    }

    private fun NavController.toCategoryBottomSheet() {
        val action = SearchFragmentDirections.actionSearchFragmentToHomeCategorySelectFragment()
        navigate(action)
    }

    private fun enterSearch() {
        binding.etSearch.setOnEditorActionListener { textView, actionId, event ->
            var handled = false
            // 확인 버튼 눌렀을 때
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val searchTerm = if (binding.etSearch.text.toString() == "") {
                    null
                } else {
                    binding.etSearch.text.toString()
                }
                viewModel.setSearchTerm(searchTerm)

                viewModel.getProducts(searchTerm = searchTerm, category = parentViewModel.category.value)
                // 키보드 내려가기
                val inputMethodManager =
                    activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                handled = true
            }
            handled
        }
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
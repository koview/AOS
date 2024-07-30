package com.example.koview.presentation.ui.main.home.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentSearchBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.ProductEvent
import com.example.koview.presentation.ui.main.global.ProductViewModel
import com.example.koview.presentation.ui.main.home.HomeEvent
import com.example.koview.presentation.ui.main.home.HomeViewModel
import com.example.koview.presentation.ui.main.home.search.adapter.SearchProductAdapter
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by activityViewModels()
    private val parentViewModel: HomeViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: SearchProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.parentVm = parentViewModel

        productAdapter = SearchProductAdapter(productViewModel)

        initSearchProductRecyclerview()
        initEventObserve()
        initProductListObserver()
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

                    is ProductEvent.ClickTag -> clickTag(productViewModel.searchProductUrl.value)
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
            viewModel.searchProductList.collect { searchProductList ->
                productAdapter.submitList(searchProductList)
            }
        }
    }

    private fun NavController.toProductDetail(searchProduct: SearchProduct) {
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
                // TODO: 여기서 ViewModel 함수 호출
                viewModel.search()
                // 키보드 내려가기
                val inputMethodManager =
                    activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                handled = true
            }
            handled
        }
    }

    private fun clickTag(url: String?) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

}
package com.example.koview.presentation.ui.main.home.product

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentHarmfulProductBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.ProductViewModel
import com.example.koview.presentation.ui.main.home.HomeEvent
import com.example.koview.presentation.ui.main.home.HomeViewModel
import com.example.koview.presentation.ui.main.home.search.SearchFragmentDirections
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.example.koview.presentation.ui.main.home.search.adapter.SearchProductAdapter

class HarmfulProductFragment :
    BaseFragment<FragmentHarmfulProductBinding>(R.layout.fragment_harmful_product) {

    private val viewModel: HarmfulProductViewModel by activityViewModels()
    private val parentViewModel: HomeViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: SearchProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.parentVm = parentViewModel

        productAdapter = SearchProductAdapter(productViewModel)

        initSearchProductRecyclerview()
        initHarmfulProductListObserver()
        initEventObserve()
    }

    private fun initSearchProductRecyclerview() {
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = productAdapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            parentViewModel.event.collect() {
                when(it) {
                    HomeEvent.ShowCategoryBottomSheet -> findNavController().toCategoryBottomSheet()
                    else -> {}
                }
            }
        }
    }

    private fun initHarmfulProductListObserver() {
        repeatOnStarted {
            viewModel.harmfulProductList.collect { harmfulProductList ->
                productAdapter.submitList(harmfulProductList)
                Log.d("유해", harmfulProductList.toString())
            }
        }
    }

    private fun NavController.toCategoryBottomSheet() {
        val action = HarmfulProductFragmentDirections.actionHarmfulProductFragmentToHomeCategorySelectFragment()
        navigate(action)
    }
}
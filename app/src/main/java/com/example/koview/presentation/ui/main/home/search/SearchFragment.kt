package com.example.koview.presentation.ui.main.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentSearchBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.home.search.adapter.SearchProductAdapter
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct
import com.example.koview.presentation.ui.main.home.search.model.TagShop

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initSearchProductRecyclerview()
        initEventObserve()
    }

    private fun initSearchProductRecyclerview() {
        val adapter = SearchProductAdapter(viewModel)
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = adapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect { event ->
                when(event) {
                    is SearchEvent.NavigateToProductDetail -> {
                        viewModel.searchProduct.value?.let { searchProduct ->
                            findNavController().toProductDetail(searchProduct)
                        }
                    }
                }
            }
        }
    }

    private fun NavController.toProductDetail(searchProduct: SearchProduct) {
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(searchProduct)
        navigate(action)
    }

}
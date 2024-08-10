package com.example.koview.presentation.ui.main.home.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.koview.R
import com.example.koview.databinding.FragmentHomeCategoryBottomSheetBinding
import com.example.koview.presentation.ui.main.home.HomeViewModel
import com.example.koview.presentation.ui.main.home.product.HarmfulProductViewModel
import com.example.koview.presentation.ui.main.home.product.PopularProductViewModel
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class HomeCategorySelectFragment() : BottomSheetDialogFragment() {

    private val parentViewModel: HomeViewModel by activityViewModels()
    private val viewModel: HomeCategorySelectViewModel by viewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val harmfulProductViewModel: HarmfulProductViewModel by activityViewModels()
    private val popularProductViewModel: PopularProductViewModel by activityViewModels()

    private var _binding: FragmentHomeCategoryBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_category_bottom_sheet,
            container,
            false
        )
        initSelectedCategory()

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventObserve()
    }

    private fun initEventObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    is HomeCategorySelectEvent.ApplySelectedCategory -> {
                        if (it.filter == null) {
                            val selectCategory = parentViewModel.category.value
                            parentViewModel.applyFilter(selectCategory)
                            searchViewModel.getProducts(searchTerm = searchViewModel.searchTerm.value, category = selectCategory)
                            harmfulProductViewModel.getProducts(category = selectCategory)
                            popularProductViewModel.getProducts(category = selectCategory)
                        } else {
                            parentViewModel.applyFilter(it.filter)
                            searchViewModel.getProducts(searchTerm = searchViewModel.searchTerm.value, category = it.filter)
                            harmfulProductViewModel.getProducts(category = it.filter)
                            popularProductViewModel.getProducts(category = it.filter)
                        }
                        dismiss()
                    }
                }
            }
        }
    }

    private fun initSelectedCategory() {
        viewModel.setCategory(parentViewModel.category.value)
    }
}
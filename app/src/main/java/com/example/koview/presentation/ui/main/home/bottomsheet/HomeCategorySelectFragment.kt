package com.example.koview.presentation.ui.main.home.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.koview.R
import com.example.koview.databinding.FragmentHomeCategoryBottomSheetBinding
import com.example.koview.presentation.ui.main.home.HomeViewModel
import com.example.koview.presentation.ui.main.home.model.Category
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class HomeCategorySelectFragment() : BottomSheetDialogFragment() {

    private val parentViewModel: HomeViewModel by activityViewModels()
    private val viewModel: HomeCategorySelectViewModel by viewModels()

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
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventObserve()
        initSelectedCategory()
    }

    private fun initEventObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    is HomeCategorySelectEvent.ApplySelectedCategory -> {
                        if (it.filter == null) {
                            parentViewModel.applyFilter(parentViewModel.category.value)
                        } else {
                            parentViewModel.applyFilter(it.filter)
                        }
                        dismiss()
                    }

                    is HomeCategorySelectEvent.ChangeCategory -> setColors(it.curCategory)
                }
            }
        }
    }

    private fun initSelectedCategory() {
        setColors(parentViewModel.category.value)
    }

    private fun setColors(category: Category) {
        val main3Color = ContextCompat.getColor(requireContext(), R.color.kv_main3)

        resetColors()
        when (category) {
            Category.ACCESSORIES -> {
                binding.ivAccessories.setColorFilter(main3Color)
                binding.tvAccessories.setTextColor(main3Color)
            }

            Category.TOY -> {
                binding.ivToy.setColorFilter(main3Color)
                binding.tvToy.setTextColor(main3Color)
            }

            Category.COOK -> {
                binding.ivCook.setColorFilter(main3Color)
                binding.tvCook.setTextColor(main3Color)
            }

            Category.CLOTHES -> {
                binding.ivClothes.setColorFilter(main3Color)
                binding.tvClothes.setTextColor(main3Color)
            }
        }
    }

    private fun resetColors() {
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
        binding.tvAccessories.setTextColor(defaultColor)
        binding.ivAccessories.setColorFilter(defaultColor)
        binding.tvToy.setTextColor(defaultColor)
        binding.ivToy.setColorFilter(defaultColor)
        binding.tvCook.setTextColor(defaultColor)
        binding.ivCook.setColorFilter(defaultColor)
        binding.tvClothes.setTextColor(defaultColor)
        binding.ivClothes.setColorFilter(defaultColor)
    }
}
package com.example.koview.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentHomeBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.home.adapter.HomeAskAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()
    private val askAdapter = HomeAskAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        setRecyclerView()
        initAskListObserver()
        initEventObserve()
    }

    private fun setRecyclerView() {
        binding.rvAskList.adapter = askAdapter
        binding.rvAskList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initAskListObserver() {
        repeatOnStarted {
            viewModel.askList.collect { askList ->
                askAdapter.submitList(askList)
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect() {
                when (it) {
                    HomeEvent.ShowCategoryBottomSheet -> findNavController().toCategoryBottomSheet()
                    HomeEvent.NavigateToHarmfulProduct -> findNavController().toHarmfulProduct()
                    HomeEvent.NavigateToPopularProduct -> findNavController().toPopularProduct()
                    HomeEvent.NavigateToSearch -> findNavController().toSearch()
                    is HomeEvent.ShowToastMessage -> showToastMessage(it.msg)
                }
            }
        }
    }

    private fun NavController.toCategoryBottomSheet() {
        val action = HomeFragmentDirections.actionHomeFragmentToHomeCategorySelectFragment()
        navigate(action)
    }

    private fun NavController.toHarmfulProduct() {
        val action = HomeFragmentDirections.actionHomeFragmentToHarmfulProductFragment()
        navigate(action)
    }

    private fun NavController.toPopularProduct() {
        val action = HomeFragmentDirections.actionHomeFragmentToPopularProductFramgnet()
        navigate(action)
    }

    private fun NavController.toSearch() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        navigate(action)
    }
}
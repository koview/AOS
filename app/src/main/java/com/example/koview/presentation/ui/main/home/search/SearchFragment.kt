package com.example.koview.presentation.ui.main.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentSearchBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.home.search.adapter.SearchProductAdapter

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initSearchProductRecyclerview()
    }

    private fun initSearchProductRecyclerview() {
        val adapter = SearchProductAdapter()
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = adapter
    }
}
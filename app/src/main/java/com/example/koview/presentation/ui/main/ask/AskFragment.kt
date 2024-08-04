package com.example.koview.presentation.ui.main.ask

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentAskBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.ask.adapter.AskAdapter

class AskFragment : BaseFragment<FragmentAskBinding>(R.layout.fragment_ask) {

    private val viewModel: AskViewModel by viewModels()
    private val askAdapter = AskAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAskRecyclerview()
        initProductListObserver()
    }

    private fun initAskRecyclerview() {
        binding.rvAsk.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAsk.adapter = askAdapter
    }

    // AskViewModel data
    private fun initProductListObserver() {
        repeatOnStarted {
            viewModel.askList.collect { searchProductList ->
                askAdapter.submitList(searchProductList)
            }
        }
    }
}
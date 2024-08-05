package com.example.koview.presentation.ui.main.ask.askdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.koview.R
import com.example.koview.databinding.FragmentAskDetailBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.ask.AskViewModel

class AskDetailFragment: BaseFragment<FragmentAskDetailBinding>(R.layout.fragment_ask_detail) {

    private val viewModel: AskDetailViewModel by viewModels()
    private val parentViewModel: AskViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.model = parentViewModel.askDetail.value
        Log.d("질문", parentViewModel.askDetail.value.toString())
        binding.vm = viewModel
    }

    private fun initEventObserve() {
        repeatOnStarted {

        }
    }
}
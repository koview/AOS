package com.example.koview.presentation.ui.main.ask.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.koview.R
import com.example.koview.databinding.FragmentAskPostBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AskPostFragment : BaseFragment<FragmentAskPostBinding>(R.layout.fragment_ask_post) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: AskPostViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserver()
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.event.collect {
                   parentViewModel.goToSetProfileImage()
            }
        }
    }
}
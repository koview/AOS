package com.example.koview.presentation.ui.main.coview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.koview.R
import com.example.koview.databinding.FragmentCoviewBinding
import com.example.koview.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoviewFragment : BaseFragment<FragmentCoviewBinding>(R.layout.fragment_coview) {

    private val viewModel: CoviewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
    }
}
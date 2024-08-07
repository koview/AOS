package com.example.koview.presentation.ui.main.mypage.setting.point

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentSettingLoginInfoBinding
import com.example.koview.databinding.FragmentSettingPointBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.mypage.setting.logininfo.LoginInfoEvent

class SettingPointFragment : BaseFragment<FragmentSettingPointBinding>(R.layout.fragment_setting_point){

    private val viewModel: SettingPointFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()

    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    PointEvent.NavigateToBack -> findNavController().navigateUp()
                }
            }
        }
    }
}
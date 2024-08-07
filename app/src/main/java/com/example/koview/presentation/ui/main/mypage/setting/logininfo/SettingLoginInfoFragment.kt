package com.example.koview.presentation.ui.main.mypage.setting.logininfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageSettingBinding
import com.example.koview.databinding.FragmentSettingLoginInfoBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.mypage.setting.MypageSettingEvent


class SettingLoginInfoFragment : BaseFragment<FragmentSettingLoginInfoBinding>(R.layout.fragment_setting_login_info){

    private val viewModel: SettingLoginInfoFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()

    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    LoginInfoEvent.NavigateToBack -> findNavController().navigateUp()
                }
            }
        }
    }
}
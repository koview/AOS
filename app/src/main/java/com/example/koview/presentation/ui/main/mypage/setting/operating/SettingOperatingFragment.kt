package com.example.koview.presentation.ui.main.mypage.setting.operating

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageSettingBinding
import com.example.koview.databinding.FragmentSettingOperatingBinding
import com.example.koview.presentation.base.BaseFragment


class SettingOperatingFragment : BaseFragment<FragmentSettingOperatingBinding>(R.layout.fragment_setting_operating){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.frag = this

    }
    fun navigateBack() {
        findNavController().navigateUp()
    }
}
package com.example.koview.presentation.ui.main.mypage.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageSettingBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.intro.signup.setinfo.SetInfoEvent

class MyPageSettingFragment : BaseFragment<FragmentMypageSettingBinding>(R.layout.fragment_mypage_setting){

    private val viewModel: MyPageSettingFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    MypageSettingEvent.NavigateToBack -> findNavController().navigateUp()
                    MypageSettingEvent.NavigateToPoint -> findNavController().toPoint()
                    MypageSettingEvent.NavigateToLoginInfo -> findNavController().toLoginInfo()
                    MypageSettingEvent.NavigateToOperating -> findNavController().toOperating()
                }
            }
        }
    }


    private fun NavController.toPoint() {
        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToSettingPointFragment()
        navigate(action)
    }

    private fun NavController.toLoginInfo() {
        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToSettingLoginInfoFragment()
        navigate(action)
    }

    private fun NavController.toOperating() {
        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToSettingOperatingFragment()
        navigate(action)
    }
}
package com.example.koview.presentation.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageBinding
import com.example.koview.presentation.base.BaseFragment

class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is MypageEvent.NavigateToSetting -> findNavController().toSetting()
                }
            }
        }
    }

    private fun NavController.toSetting() {
        val action = MyPageFragmentDirections.actionMypageFragmentToMyPageSettingFragment()
        navigate(action)
    }
}
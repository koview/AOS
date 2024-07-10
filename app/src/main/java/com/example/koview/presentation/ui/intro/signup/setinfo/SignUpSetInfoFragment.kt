package com.example.koview.presentation.ui.intro.signup.setinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentSignupSetInfoBinding
import com.example.koview.presentation.base.BaseFragment

class SignUpSetInfoFragment : BaseFragment<FragmentSignupSetInfoBinding>(R.layout.fragment_signup_set_info) {

    private val viewModel: SignUpSetInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    SetInfoEvent.NavigateToBack -> findNavController().navigateUp()
                    SetInfoEvent.NavigateToComplete -> findNavController().toComplete()
                }
            }
        }
    }

    private fun NavController.toComplete() {
        val action = SignUpSetInfoFragmentDirections.actionSignupSetInfoFragmentToSignupCompleteFragment()
        navigate(action)
    }
}
package com.example.koview.presentation.ui.intro.signup.complete

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentSignupCompleteBinding
import com.example.koview.presentation.base.BaseFragment

class SignUpCompleteFragment :
    BaseFragment<FragmentSignupCompleteBinding>(R.layout.fragment_signup_complete) {

    private val viewModel: SignUpCompleteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    SignUpCompleteEvent.NavigateToLogin -> findNavController().toComplete()
                }
            }
        }
    }

    private fun NavController.toComplete() {
        val action = SignUpCompleteFragmentDirections.actionSignupCompleteFragmentToLoginFragment()
        navigate(action)
    }
}
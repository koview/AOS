package com.example.koview.presentation.ui.intro.signup.selectshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentSignupSelectShopBinding
import com.example.koview.presentation.base.BaseFragment

class SignUpSelectShopFragment :
    BaseFragment<FragmentSignupSelectShopBinding>(R.layout.fragment_signup_select_shop) {

    private val viewModel: SignUpSelectShopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    SelectShopEvent.NavigateToBack -> findNavController().navigateUp()
                    SelectShopEvent.NavigateToSetInfo -> findNavController().toSetInfo()
                }
            }
        }
    }

    private fun NavController.toSetInfo() {
        val action = SignUpSelectShopFragmentDirections.actionSignupSelectShopFragmentToSignupSetInfoFragment()
        navigate(action)
    }
}
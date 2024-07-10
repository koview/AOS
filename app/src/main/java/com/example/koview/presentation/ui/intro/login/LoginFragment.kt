package com.example.koview.presentation.ui.intro.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentLoginBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.MainActivity

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is LoginEvent.NavigateToMainActivity -> {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                    is LoginEvent.NavigateToSignUp -> findNavController().toSignUp()
                }
            }
        }
    }

    private fun NavController.toSignUp() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignupShopFragment()
        navigate(action)
    }
}
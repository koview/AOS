package com.example.koview.presentation.ui.intro.signup.setinfo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.koview.R
import com.example.koview.databinding.FragmentSignupSetInfoBinding
import com.example.koview.presentation.base.BaseFragment

class SignUpSetInfoFragment : BaseFragment<FragmentSignupSetInfoBinding>(R.layout.fragment_signup_set_info) {

    private val viewModel: SignUpSetInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        // 선택한 상점 데이터 꺼낸 후 뷰모델에 저장
        val args: SignUpSetInfoFragmentArgs by navArgs()
        val selectedShops: List<String> = args.checkedShops.toList()
        viewModel.selectedShops.value = selectedShops

// To-do : 잘 들어오는 지에 대한 것이기 때문에 나중에 삭제
        showToastMessage(selectedShops.toString())
        initEventObserve()
        setObserve()
        clickSignupBtn()

    }

    private fun clickSignupBtn(){
        binding.btnSignup.setOnClickListener {
            Log.d("SignUpSetInfoFragment", viewModel.getSignupDataToString())
            viewModel.navigateToNext()
        }
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
        val action =
            SignUpSetInfoFragmentDirections.actionSignupSetInfoFragmentToSignupCompleteFragment()
        navigate(action)
    }


    private fun setObserve() {
        // 비밀번호, 비밀번호 재확인에 값이 들어있을때만 오류나 일치가 뜨게
        viewModel.passwordCheck.observe(viewLifecycleOwner) {
            if(viewModel.passwordCheck.value.toString().equals("")){
                viewModel.passwordCheckVisible.value = false
                binding.tlPasswordCheck.error = null
            } else if(!viewModel.passwordCheck()) {
                binding.tlPasswordCheck.error = "비밀번호가 일치하지 않습니다."
                viewModel.passwordCheckVisible.value = false
            } else {
                binding.tlPasswordCheck.error = null
                viewModel.passwordCheckVisible.value = true
            }
        }

        //
        viewModel.signUpBtnOn.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnSignup.setBackgroundResource(R.drawable.rect_main3fill_nostroke_12radius)
                binding.btnSignup.isClickable = true
            } else {
                binding.btnSignup.setBackgroundResource(R.drawable.rect_blackfill_nostroke_12radius)
                binding.btnSignup.isClickable = false
            }
        }

    }
}
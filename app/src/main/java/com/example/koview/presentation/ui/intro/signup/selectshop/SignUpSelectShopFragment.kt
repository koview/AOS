package com.example.koview.presentation.ui.intro.signup.selectshop

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentSignupSelectShopBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.MainActivity

class SignUpSelectShopFragment :
    BaseFragment<FragmentSignupSelectShopBinding>(R.layout.fragment_signup_select_shop) {

    private val viewModel: SignUpSelectShopViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserve()

        val checkboxes = listOf(
            binding.cbAmazon,
            binding.cbEBay,
            binding.cbAliexpress,
            binding.cbWalmart,
            binding.cbTarget
        )

        // 화면 넘어가며 _checkedTexts에 선택한 가게들 업데이트
// To-do : 선택된 쇼핑리스트 저장
        binding.btnSignup.setOnClickListener {
            findCheckedShop(checkboxes)
            Log.d("SignUpSelectShopFragment", "Selected Shops String: ${viewModel.getCheckedShopsString()}")
            viewModel.navigateToNext()
        }


        checkBoxObserve()

    }

    // 체크박스 중 하나라도 체크 될 시를 구독하며 버튼 상태 변경
    private fun checkBoxObserve(){
        viewModel.isAnyCheckboxChecked.observe(viewLifecycleOwner){
            if(it){
                binding.btnSignup.setBackgroundResource(R.drawable.rect_main3fill_nostroke_12radius)
                binding.btnSignup.isClickable = true
            } else {
                binding.btnSignup.setBackgroundResource(R.drawable.rect_blackfill_nostroke_12radius)
                binding.btnSignup.isClickable = false
            }

        }
    }

    // 최종적으로 체크된 shop들을 뷰모델의 라이브데이터에 저장
    private fun findCheckedShop(checkboxes: List<CheckBox>) {
        val checkedShops = checkboxes.filter { it.isChecked }.map { it.text.toString() }
        viewModel.updateCheckedShops(checkedShops)
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    SelectShopEvent.NavigateToBack -> findNavController().navigateUp()
                    is SelectShopEvent.NavigateToSetInfo -> findNavController().toSetInfo(viewModel.checkedTexts.value!!.toTypedArray())
                }
            }
        }
    }

    private fun NavController.toSetInfo(checkedShops: Array<String>) {
        val action = SignUpSelectShopFragmentDirections.actionSignupSelectShopFragmentToSignupSetInfoFragment(checkedShops)
        navigate(action)
    }
}
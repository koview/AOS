package com.example.koview.presentation.ui.main.mypage.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageSettingBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.intro.signup.setinfo.SetInfoEvent
import com.example.koview.presentation.ui.main.mypage.MyPageFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageSettingFragment : BaseFragment<FragmentMypageSettingBinding>(R.layout.fragment_mypage_setting){

    private val viewModel: MyPageSettingFragmentViewModel by viewModels()
    private val parentViewModel: MyPageFragmentViewModel by activityViewModels() //액티비티의 주기를 가진 뷰모델
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        observeViewModel()
    }

    private fun observeViewModel() {
        // 닉네임 관찰
        lifecycleScope.launch {
            parentViewModel.nickname.collect { nickname ->
                binding.tvNickname.text = "안녕하세요, ${nickname}!"
            }
        }

        // 프로필 이미지 관찰
        lifecycleScope.launch {
            parentViewModel.profileImg.collect { imageUrl ->
                // Glide를 사용하여 이미지 로드
                Glide.with(this@MyPageSettingFragment)
                    .load(imageUrl)
                    .error(R.drawable.ic_profile) // 오류 발생 시 표시할 이미지
                    .into(binding.ivProfile)
            }
        }
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
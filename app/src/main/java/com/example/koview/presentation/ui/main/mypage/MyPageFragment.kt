package com.example.koview.presentation.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageBinding
import com.example.koview.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        viewModel.getMyDetail()
        observeViewModel()
        initEventObserve()
    }

    private fun observeViewModel() {
        // 닉네임 관찰
        lifecycleScope.launch {
            viewModel.nickname.collect { nickname ->
                binding.tvNickname.text = nickname
            }
        }

        // 프로필 이미지 관찰
        lifecycleScope.launch {
            viewModel.profileImg.collect { imageUrl ->
                // Glide를 사용하여 이미지 로드
                Glide.with(this@MyPageFragment)
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
package com.example.koview.presentation.ui.main.mypage.setting.logininfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.app.App.Companion.sharedPreferences
import com.example.koview.databinding.FragmentSettingLoginInfoBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.intro.IntroActivity
import com.example.koview.presentation.ui.main.mypage.ConfirmDialog
import com.example.koview.presentation.ui.main.mypage.ConfirmDialogInterface
import com.example.koview.presentation.ui.main.mypage.MyPageFragmentViewModel
import com.example.koview.presentation.utils.Constants.ACCESS_TOKEN
import com.example.koview.presentation.utils.Constants.REFRESH_TOKEN


class SettingLoginInfoFragment :
    BaseFragment<FragmentSettingLoginInfoBinding>(R.layout.fragment_setting_login_info),
    ConfirmDialogInterface {

    private val parentViewModel: MyPageFragmentViewModel by activityViewModels()
    private val viewModel: SettingLoginInfoFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserver()
        initProfileInfo()
        settingListClickEvent()
    }

    // 다이얼로그 이벤트 설정
    private fun settingListClickEvent() {
        with(binding) {

            // 로그아웃
            loLogout.setOnClickListener {
                logout()
            }

            // 회원탈퇴
            loQuit.setOnClickListener {
                quit()
            }
        }
    }

    private fun logout() {
        // 다이얼로그
        val title = "로그아웃 하시겠어요?"

        val dialog = ConfirmDialog(this@SettingLoginInfoFragment, title, null, 1)
        // 알림창이 띄워져있는 동안 배경 클릭 막기
        dialog.isCancelable = false
        activity?.let { dialog.show(it.supportFragmentManager, "ConfirmDialog") }
    }

    private fun quit() {
        // 다이얼로그
        val title = "정말 탈퇴 하시겠어요?"
        val content = "탈퇴하시면 회원님이 작성하신 게시글과 댓글은 남아있지만, 회원님의 계정 정보가 모두 사라집니다. 계속 진행하시겠습니까?"

        val dialog = ConfirmDialog(this@SettingLoginInfoFragment, title, content, 2)
        // 알림창이 띄워져있는 동안 배경 클릭 막기
        dialog.isCancelable = false
        activity?.let { dialog.show(it.supportFragmentManager, "ConfirmDialog") }
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    LoginInfoEvent.NavigateToBack -> findNavController().navigateUp()
                    LoginInfoEvent.NavigateToLogin -> {
                        startActivity(Intent(requireContext(), IntroActivity::class.java))
                        activity?.finish()
                    }
                }
            }
        }
    }


    private fun initProfileInfo() {
        repeatOnStarted {
            parentViewModel.profileImg.collect { imageUrl ->
                // Glide를 사용하여 이미지 로드
                Glide.with(this@SettingLoginInfoFragment)
                    .load(imageUrl)
                    .error(R.drawable.ic_profile) // 오류 발생 시 표시할 이미지
                    .into(binding.ivProfile)
            }
        }

        viewModel.setNickname(parentViewModel.nickname.value)
    }

    override fun onClickYesButton(id: Int) {
        if (id == 1) { // 로그아웃
            // token 삭제
            sharedPreferences.edit()
                .remove(ACCESS_TOKEN)
                .remove(REFRESH_TOKEN)
                .apply()

            viewModel.navigateToLogin()
        } else if (id == 2) {
            // todo: 회원 탈퇴
        }
    }
}
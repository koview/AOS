package com.example.koview.presentation.ui.main.mypage.setting.logininfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageSettingBinding
import com.example.koview.databinding.FragmentSettingLoginInfoBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.mypage.ConfirmDialog
import com.example.koview.presentation.ui.main.mypage.ConfirmDialogInterface
import com.example.koview.presentation.ui.main.mypage.setting.MypageSettingEvent


class SettingLoginInfoFragment : BaseFragment<FragmentSettingLoginInfoBinding>(R.layout.fragment_setting_login_info),
    ConfirmDialogInterface {

    private val viewModel: SettingLoginInfoFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()

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

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    LoginInfoEvent.NavigateToBack -> findNavController().navigateUp()
                }
            }
        }
    }

    override fun onClickYesButton(id: Int) {
        if (id == 1) { // 로그아웃
        }
        else if (id == 2) { // 회원탈퇴
        }
    }
}
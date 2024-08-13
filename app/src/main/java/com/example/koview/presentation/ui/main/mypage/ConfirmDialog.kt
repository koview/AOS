package com.example.koview.presentation.ui.main.mypage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.koview.databinding.DialogConfirmBinding

interface ConfirmDialogInterface {
    fun onClickYesButton(id: Int)
}
class ConfirmDialog(
    confirmDialogInterface: ConfirmDialogInterface,
    title: String, content: String?, id: Int
) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogConfirmBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: ConfirmDialogInterface? = null

    private var title: String? = null
    private var content: String? = null
    // id = 0 -> 리뷰 삭제, id = 1 -> 로그 아웃, id = 2 -> 회원 탈퇴
    private var id: Int? = null

    init {
        this.title = title
        this.content = content
        this.id = id
        this.confirmDialogInterface = confirmDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogConfirmBinding.inflate(inflater, container, false)
        val view = binding.root

        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 제목
        binding.tvTitle.text = title
        // 내용
        if (content == null) {
            binding.tvContent.visibility = View.GONE
        } else {
            binding.tvContent.text = content
        }

        // 취소 버튼 클릭
        binding.noButton.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.yesButton.setOnClickListener {
            this.confirmDialogInterface?.onClickYesButton(id!!)
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
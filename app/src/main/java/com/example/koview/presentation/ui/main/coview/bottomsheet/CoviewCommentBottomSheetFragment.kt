package com.example.koview.presentation.ui.main.coview.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.databinding.FragmentCoviewCommentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoviewCommentBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCoviewCommentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val args: CoviewCommentBottomSheetFragmentArgs by navArgs()
    private val reviewId by lazy { args.reviewId }
    private val profileImgUrl by lazy { args.profileImg }
    private val isFullView by lazy { args.isFullView }

    private lateinit var behavior: BottomSheetBehavior<View>
    private val viewModel: CoviewCommentBottomSheetViewModel by viewModels()

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_coview_comment_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        behavior = BottomSheetBehavior.from(bottomSheet)

        if (isFullView) {
            // 전체 화면을 꽉 채우도록 설정
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            // 반만 채우도록 설정
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            val displayMetrics = resources.displayMetrics
            bottomSheet.layoutParams.height = (displayMetrics.heightPixels * 0.6).toInt()
        }
        bottomSheet.requestLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        viewModel.getComment()
        setProfileImage()
        editTextListener()

        Log.d("코뷰", "리뷰 id : $reviewId")
    }

    // 댓글 입력 필드 프로필 이미지 설정
    private fun setProfileImage() {

        if (profileImgUrl.isNullOrEmpty()) {
            Glide.with(binding.root.context)
                .load(R.drawable.img_review_ex)
                .into(binding.ivMyProfile)
        } else {
            Glide.with(binding.root.context)
                .load(profileImgUrl)
                .error(R.drawable.img_review_ex)
                .into(binding.ivMyProfile)
        }
    }

    private fun editTextListener() {
        binding.etComment.setOnClickListener {
            // EditText 클릭 시 BottomSheet 높이를 전체 화면으로 변경
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.root.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            binding.root.requestLayout()
        }
    }

}
package com.example.koview.presentation.ui.main.coview.bottomsheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.koview.presentation.customview.LoadingDialog
import com.example.koview.presentation.ui.main.coview.adapter.CoviewCommentAdapter
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

    private lateinit var loadingDialog: LoadingDialog
    private var loadingState = false

    private val args: CoviewCommentBottomSheetFragmentArgs by navArgs()
    private val reviewId by lazy { args.reviewId }
    private val profileImgUrl by lazy { args.profileImg }
    private val isFullView by lazy { args.isFullView }

    private lateinit var behavior: BottomSheetBehavior<View>
    private val viewModel: CoviewCommentBottomSheetViewModel by viewModels()

    private var adapter: CoviewCommentAdapter? = null

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    private fun showLoading(context: Context) {
        if (!loadingState) {
            loadingDialog = LoadingDialog(context)
            loadingDialog.show()
            loadingState = true
        }
    }

    private fun dismissLoading() {
        if (loadingState) {
            loadingDialog.dismiss()
            loadingState = false
        }
    }

    private fun showToastMessage(message: String) {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast.show()
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

        adapter = CoviewCommentAdapter()
        binding.rvComment.adapter = adapter

        viewModel.getComment(reviewId)
        initEventObserver()
        initStateObserver()
        setProfileImage()
        initClickListener()

        Log.d("코뷰", "리뷰 id : $reviewId")
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is CoviewCommentEvent.ShowToastMessage -> showToastMessage(it.msg)
                    CoviewCommentEvent.DismissLoading -> dismissLoading()
                    CoviewCommentEvent.ShowLoading -> showLoading(requireContext())
                }
            }
        }
    }

    private fun initStateObserver() {
        repeatOnStarted {
            viewModel.uiState.collect {
                adapter?.submitList(it.commentList)
                binding.tvNoComments.visibility =
                    if (it.isCommentListEmpty) View.VISIBLE else View.GONE

            }
        }

        repeatOnStarted {
            viewModel.comment.collect {
                if (it.isNotBlank()) {
                    binding.btnSend.isEnabled = true
                } else {
                    binding.btnSend.isEnabled = false
                }
            }
        }

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

    private fun initClickListener() {

        binding.btnSend.setOnClickListener {
            viewModel.addComment(reviewId)
        }

        binding.etComment.setOnClickListener {
            if (!isFullView) {
                // EditText 클릭 시 BottomSheet 높이를 전체 화면으로 변경
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.root.post {
                    binding.root.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                    binding.root.requestLayout()
                }
                Log.d("코뷰 댓글", "전체 화면으로 전환")
            }
        }
    }

}
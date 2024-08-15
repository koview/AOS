package com.example.koview.presentation.ui.main.ask.askanswer

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.data.model.requeset.QueryAnswerRequest
import com.example.koview.databinding.FragmentAskAnswerBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.ask.AskViewModel
import com.example.koview.presentation.ui.main.ask.askanswer.adapter.AskAnswerReviewAdapter
import com.example.koview.presentation.ui.main.ask.askdetail.AskDetailViewModel

class AskAnswerFragment : BaseFragment<FragmentAskAnswerBinding>(R.layout.fragment_ask_answer),
    AskAnswerInterface {

    private val viewModel: AskAnswerViewModel by activityViewModels()
    private val parentViewModel: AskViewModel by activityViewModels()
    private val askDetailViewModel: AskDetailViewModel by activityViewModels()
    private val askAnswerReviewAdapter = AskAnswerReviewAdapter(this)
    private var longClickReviewId: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initReviewListObserver()
        initAskAnswerReviewRecyclerview()
        setupAnswerTextWatcher()
        initEventObserve()
    }

    // 리뷰 데이터
    private fun initReviewListObserver() {
        repeatOnStarted {
            viewModel.reviewList.collect { reviewList ->
                askAnswerReviewAdapter.submitList(reviewList)
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    AskAnswerEvent.NavigateToAskDetail -> findNavController().toAskDetail()
                }
            }
        }
    }

    private fun initAskAnswerReviewRecyclerview() {
        binding.rvReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = askAnswerReviewAdapter
    }

    private fun setupAnswerTextWatcher() {
        binding.etAnswer.addTextChangedListener {
            updateSubmitButtonState()
        }
    }

    private fun updateSubmitButtonState() {
        val isAnswerNotEmpty = binding.etAnswer.text.toString().isNotEmpty()
        val isReviewSelected = longClickReviewId != 0L

        if (isAnswerNotEmpty && isReviewSelected) {
            binding.btnReview.isEnabled = true
            binding.btnReview.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#024FCF"))
            binding.btnReview.setOnClickListener {
                postAskAnswer()
                parentViewModel.askDetail.value?.let { it1 -> parentViewModel.updateAnswer(it1) }
            }
        } else {
            binding.btnReview.isEnabled = false
            binding.btnReview.backgroundTintList =
                ColorStateList.valueOf(Color.BLACK) // 비활성화된 상태에서의 색상
        }
    }

    private fun postAskAnswer() {
        parentViewModel.askDetail.value?.let {
            viewModel.postQueryAnswer(
                it.queryId,
                QueryAnswerRequest(binding.etAnswer.text.toString(), longClickReviewId)
            )
        }
    }

    override fun onReviewLongClick(reviewId: Long) {
        longClickReviewId = reviewId
        updateSubmitButtonState()
    }

    private fun NavController.toAskDetail() {
        val action = AskAnswerFragmentDirections.actionAskAnswerFragmentToAskDetailFragment()
        navigate(action)
    }
}
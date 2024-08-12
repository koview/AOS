package com.example.koview.presentation.ui.main.ask.askanswer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentAskAnswerBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.ask.askanswer.adapter.AskAnswerReviewAdapter

class AskAnswerFragment : BaseFragment<FragmentAskAnswerBinding>(R.layout.fragment_ask_answer) {

    private val viewModel: AskAnswerViewModel by activityViewModels()
    private val askAnswerReviewAdapter = AskAnswerReviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initReviewListObserver()
        initAskAnswerReviewRecyclerview()
    }

    // 리뷰 데이터
    private fun initReviewListObserver() {
        repeatOnStarted {
            viewModel.reviewList.collect {reviewList ->
                askAnswerReviewAdapter.submitList(reviewList)
            }
        }
    }

    private fun initAskAnswerReviewRecyclerview() {
        binding.rvReview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = askAnswerReviewAdapter
    }
}
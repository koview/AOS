package com.example.koview.presentation.ui.main.coview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.koview.R
import com.example.koview.databinding.FragmentCoviewBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.coview.adapter.CoviewReviewAdapter
import com.example.koview.presentation.ui.main.coview.adapter.OnLikeClickListener
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoviewFragment : BaseFragment<FragmentCoviewBinding>(R.layout.fragment_coview),
    OnLikeClickListener {

    private val viewModel: CoviewViewModel by viewModels()
    private var adapter: CoviewReviewAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initDataObserve()
        initAdapter()
    }

    // 리뷰 데이터 설정
    private fun initDataObserve() {
        repeatOnStarted {
            viewModel.reviewList.collect {
                adapter?.setList(it)
            }
        }
    }

    private fun initAdapter() {
        adapter = CoviewReviewAdapter(this)
        binding.rvCoviewList.adapter = adapter
    }

    override fun onLikeClick(item: CoviewUiData) {
        viewModel.onLikeClick(item)
    }
}
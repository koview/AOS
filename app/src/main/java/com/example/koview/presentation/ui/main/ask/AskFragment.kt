package com.example.koview.presentation.ui.main.ask

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.data.model.response.QueryResultList
import com.example.koview.databinding.FragmentAskBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.ask.adapter.AskAdapter
import com.example.koview.presentation.ui.main.ask.model.AskData
import com.example.koview.presentation.ui.main.global.product.ProductEvent

class AskFragment : BaseFragment<FragmentAskBinding>(R.layout.fragment_ask), AskInterface {

    private val viewModel: AskViewModel by activityViewModels()
    private lateinit var askAdapter: AskAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        askAdapter = AskAdapter(this)

        initAskRecyclerview()
        initProductListObserver()
        initEventObserve()
    }

    private fun initAskRecyclerview() {
        binding.rvAsk.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAsk.adapter = askAdapter
    }

    // AskViewModel data
    private fun initProductListObserver() {
        repeatOnStarted {
            viewModel.getQueries.collect { getQueries ->
                askAdapter.submitList(getQueries)
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {event->
                when (event) {
                    is AskEvent.NavigateToAskDetail -> {
                        viewModel.askDetail.value?.let { askDetail ->
                            findNavController().toAskDetail(askDetail)
                        }
                    }
                }
            }
        }
    }

    private fun NavController.toAskDetail(askDetail: QueryResultList) {
        val action = AskFragmentDirections.actionAskFragmentToAskDetailFragment(askDetail)
        navigate(action)
    }

    override fun onAskClick(askDetail: QueryResultList) {
        viewModel.navigateToAskDetail(askDetail)
    }

    override fun onAskIconClick(item: QueryResultList) {
//        viewModel.onAskClick(item)
    }
}
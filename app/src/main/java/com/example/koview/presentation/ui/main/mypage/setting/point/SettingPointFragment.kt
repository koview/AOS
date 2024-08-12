package com.example.koview.presentation.ui.main.mypage.setting.point

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.R
import com.example.koview.data.model.response.PointDetail
import com.example.koview.databinding.FragmentSettingLoginInfoBinding
import com.example.koview.databinding.FragmentSettingPointBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.mypage.setting.logininfo.LoginInfoEvent
import com.example.koview.presentation.ui.main.mypage.setting.point.adapter.PointDetailRVAdapter

class SettingPointFragment : BaseFragment<FragmentSettingPointBinding>(R.layout.fragment_setting_point){

    private val viewModel: SettingPointFragmentViewModel by viewModels()
    private lateinit var pointAdapter: PointDetailRVAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()

        recyclerView = binding.rvPoint
        pointAdapter = PointDetailRVAdapter(createDummyData())
        recyclerView.adapter = pointAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun createDummyData(): List<PointDetail> {
        return listOf(
            PointDetail("일일 미션: 댓글 달기", "+500P", "2024.07.17"),
            PointDetail("일일 미션: 리뷰 게시글 올리기", "+800P", "2024.07.13"),
            PointDetail("일일 미션: 리뷰 게시글 올리기", "+800P", "2024.07.10"),
            PointDetail("랜덤 포인트", "+150P", "2024.07.09")
            )
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    PointEvent.NavigateToBack -> findNavController().navigateUp()
                }
            }
        }
    }
}
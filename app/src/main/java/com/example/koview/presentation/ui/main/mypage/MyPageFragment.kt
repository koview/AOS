package com.example.koview.presentation.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.databinding.FragmentMypageBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.mypage.adapter.ReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageFragmentViewModel by activityViewModels()
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        viewModel.getMyDetail()
        observeViewModel()
        initEventObserve()


        recyclerView = binding.rvMyReview
        reviewsAdapter = ReviewsAdapter(emptyList())
        recyclerView.adapter = reviewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeRvViewModel()
        viewModel.getMyReviews() // 초기 리뷰 가져오기

        // 스크롤 리스너 설정 (페이징)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { // 스크롤이 끝에 도달했을 때
                    viewModel.getMyReviews() // 다음 페이지 리뷰 가져오기
                }
            }
        })
        setMyReviewOnClick()


    }

    private fun setMyReviewOnClick(){
        // 내 리뷰 클릭 리스너
        reviewsAdapter.setMyItemClickListener(object: ReviewsAdapter.MyItemClickListener{
            override fun onLongClick(reviewId: Long) {
                // isChecking == false 일 때 리뷰 삭제 버튼 활성화
                if (!viewModel.isChecking.value){
                    viewModel.startChecking(reviewId)
                }
            }

            override fun onItemClick(reviewId: Long) {
                // 삭제 버튼 활성화 시 리뷰 삭제 목록 추가 else 리뷰 상세 화면 이동
                if(viewModel.isChecking.value){
                    viewModel.toggleReviewId(reviewId)
                } else{
                    // todo: 리뷰 상세화면 이동
                }

                showToastMessage("${viewModel.deleteReviewList.value}")
            }
        })
    }


    private fun observeViewModel(){
        // 닉네임 관찰
        lifecycleScope.launch {
            viewModel.nickname.collect { nickname ->
                binding.tvNickname.text = nickname
            }
        }

        // 프로필 이미지 관찰
        lifecycleScope.launch {
            viewModel.profileImg.collect { imageUrl ->
                // Glide를 사용하여 이미지 로드
                Glide.with(this@MyPageFragment)
                    .load(imageUrl)
                    .error(R.drawable.ic_profile) // 오류 발생 시 표시할 이미지
                    .into(binding.ivProfile)
            }
        }
    }

    private fun observeRvViewModel() {
        // 새로운 리뷰 관찰
        viewModel.myReviews.onEach { reviews ->
            reviewsAdapter.updateReviews(reviews) // 새로운 리뷰로 어댑터 업데이트
        }.launchIn(viewLifecycleOwner.lifecycleScope) // Flow를 관찰
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is MypageEvent.NavigateToSetting -> findNavController().toSetting()
                }
            }
        }
    }

    private fun NavController.toSetting() {
        val action = MyPageFragmentDirections.actionMypageFragmentToMyPageSettingFragment()
        navigate(action)
    }
}
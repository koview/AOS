package com.example.koview.presentation.ui.main.mypage

import android.os.Bundle
import android.util.Log
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
import com.example.koview.presentation.ui.main.mypage.adapter.MyItemClickListener
import com.example.koview.presentation.ui.main.mypage.adapter.ReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage),
    ConfirmDialogInterface {

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

        binding.scrollView.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            val childHeight = binding.scrollView.getChildAt(0).measuredHeight
            val scrollViewHeight = v.height

            // NestedScrollView의 바닥에 도달했을 때
            if (scrollY >= (childHeight - scrollViewHeight)) {
                if (viewModel.hasNext) {
                    Log.d("onScrolled", "onScrolled")
                    viewModel.getMyReviews() // 다음 페이지 리뷰 가져오기
                }
            }
        }
        setMyReviewOnClick()

        binding.btnDeleteReview.setOnClickListener { dialogDeleteReviews() }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyReviews(true) // 초기 리뷰 가져오기
    }

    private fun setMyReviewOnClick() {
        // 내 리뷰 클릭 리스너
        reviewsAdapter.setMyItemClickListener(object : MyItemClickListener {
            override fun onLongClick(reviewId: Long) {
                // isChecking == false 일 때 리뷰 삭제 버튼 활성화
                if (!viewModel.isChecking.value) {
                    viewModel.startChecking(reviewId)
                }
            }

            override fun onItemClick(reviewId: Long, memberId: Long) {
                // 삭제 버튼 활성화
                if (viewModel.isChecking.value) {
                    viewModel.toggleReviewId(reviewId)  // 리뷰 삭제 목록 추가
                } else {
                    findNavController().toReviewDetail(reviewId, memberId)    // 리뷰 상세 화면 이동
                }
            }
        })
    }


    private fun observeViewModel() {
        // 닉네임 관찰
        repeatOnStarted {
            viewModel.nickname.collect { nickname ->
                binding.tvNickname.text = nickname
            }
        }

        // 프로필 이미지 관찰
        repeatOnStarted {
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
            dismissLoading()
            viewModel.checkReviewsIsEmpty()
        }.launchIn(viewLifecycleOwner.lifecycleScope) // Flow를 관찰
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    MypageEvent.NavigateToSetting -> findNavController().toSetting()
                    MypageEvent.NavigateToCreateReview -> findNavController().toCreateReview()
                    MypageEvent.ShowLoading -> showLoading(requireContext())
                    MypageEvent.DismissLoading -> dismissLoading()
                }
            }
        }
    }

    private fun NavController.toSetting() {
        val action = MyPageFragmentDirections.actionMypageFragmentToMyPageSettingFragment()
        navigate(action)
    }

    private fun NavController.toCreateReview() {
        val action = MyPageFragmentDirections.actionMypageFragmentToCreateReviewFragment()
        navigate(action)
    }

    // 리뷰 상세 화면으로 이동
    private fun NavController.toReviewDetail(reviewId: Long, memberId: Long) {
        val action =
            MyPageFragmentDirections.actionMypageFragmentToMypageReviewDetailFragment(reviewId, memberId)
        navigate(action)
    }

    private fun dialogDeleteReviews() {
        val title = "해당 리뷰를 삭제하시겠어요?"
        val dialog = ConfirmDialog(this@MyPageFragment, title, null, 0)

        // 알림창이 띄워져있는 동안 배경 클릭 막기
        dialog.isCancelable = false
        activity?.let { dialog.show(it.supportFragmentManager, "ConfirmDialog") }
    }

    override fun onClickYesButton(id: Int) {
        viewModel.deleteMyReviews()
    }
}
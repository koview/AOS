package com.example.koview.presentation.ui.main.global.createreview

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.R
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.databinding.FragmentReviewCreateBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.MainViewModel
import com.example.koview.presentation.ui.main.global.createreview.adapter.GalleryAdapter
import com.example.koview.presentation.ui.main.global.createreview.adapter.TagAdapter
import com.example.koview.presentation.ui.main.mypage.MyPageFragmentViewModel
import com.example.koview.presentation.ui.main.mypage.adapter.ReviewsAdapter
import com.example.koview.presentation.ui.main.mypage.setting.MypageSettingEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CreateReviewFragment: BaseFragment<FragmentReviewCreateBinding>(R.layout.fragment_review_create) {

    private val viewModel: CreateReviewFragmentViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var tagAdapter: TagAdapter
    private lateinit var galleryRecyclerView: RecyclerView
    private lateinit var tagRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.frag = this
        binding.vm = viewModel
        binding.etContent.movementMethod = ScrollingMovementMethod.getInstance()

        // 갤러리 리사이클러뷰 설정
        galleryRecyclerView = binding.rvGallery
        galleryAdapter = GalleryAdapter(emptyList())
        galleryRecyclerView.adapter = galleryAdapter
        galleryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // 태그 리사이클러뷰 설정
        tagRecyclerView = binding.rvTag
        tagAdapter = TagAdapter(emptyList())
        tagRecyclerView.adapter = tagAdapter
        tagRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        initEventObserve()
        observeRvViewModel()
        observeParentViewModel()
        observeBtnViewModel()
        initClickLister()
    }


    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    CreateReviewEvent.NavigateToBack -> findNavController().navigateUp()
                    else -> {}
                }
            }
        }
    }

    private fun observeRvViewModel() {
        // 새로운 태그 관찰
        viewModel.purchaseLinkList.onEach { links ->
            tagAdapter.updateLinks(links) // 새로운 태그로 어댑터 업데이트
        }.launchIn(viewLifecycleOwner.lifecycleScope) // Flow를 관찰

        // 새로운 이미지 관찰
        viewModel.imageLinkList.onEach { links ->
            val stringLinks = links.map { it.toString() }
            galleryAdapter.updateImages(stringLinks) // 새로운 태그로 어댑터 업데이트
        }.launchIn(viewLifecycleOwner.lifecycleScope) // Flow를 관찰

        // 리뷰 작성 클릭 시 에러 메시지
        viewModel.createError.onEach { message ->
            showToastMessage(message)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeParentViewModel() {
        // 새로운 이미지 관찰
        parentViewModel.imageList.onEach { links ->
            val newImageList = links
            Log.d("getGallery", "parentViewModel.imageList.value = ${newImageList}")
            viewModel.inputImage(newImageList)
        }.launchIn(viewLifecycleOwner.lifecycleScope) // Flow를 관찰
    }
    private fun observeBtnViewModel() {
        viewModel.content.onEach{
            viewModel.validate()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.purchaseLinkList.onEach{
            viewModel.validate()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.createBtnOn.onEach { btnOn ->
            if (btnOn) {
                // 버튼이 클릭 가능한 상태로 변경
                binding.btnCreateReview.isEnabled = true
                binding.btnCreateReview.setBackgroundResource(R.drawable.rect_main3fill_nostroke_12radius)
            } else {
                // 버튼이 클릭 불가능한 상태로 변경
                binding.btnCreateReview.isEnabled = false
                binding.btnCreateReview.setBackgroundResource(R.drawable.rect_blackfill_nostroke_12radius) // 비활성화 상태의 배경
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope) // Flow를 관찰

    }

    private fun initClickLister() {
        // 내 태그 클릭 리스너
        tagAdapter.setMyItemClickListener(object: TagAdapter.MyItemClickListener{
            override fun onDeleteClick(link: PurchaseLinkDTO) {
                viewModel.deleteLink(link)
            }
        })

        // 내 이미지 클릭 리스너
        galleryAdapter.setMyItemClickListener(object: GalleryAdapter.MyItemClickListener{
            override fun onDeleteClick(url: String) {
                viewModel.deleteImage(url)
            }
        })

        // 좋아요 클릭
        binding.ivLike.setOnClickListener {
            binding.ivLike.visibility = View.GONE
            binding.ivUnlike.visibility = View.VISIBLE
        }

        binding.ivUnlike.setOnClickListener {
            binding.ivLike.visibility = View.VISIBLE
            binding.ivUnlike.visibility = View.GONE
        }
    }

    fun getGallery(){
        parentViewModel.goToSetProfileImage()
    }

    fun navigateBack() {
        findNavController().navigateUp()
    }
}
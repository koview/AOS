package com.example.koview.presentation.ui.main.ask.post

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.koview.R
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.databinding.FragmentAskPostBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.MainViewModel
import com.example.koview.presentation.ui.main.ask.post.adapter.AskPostClickListener
import com.example.koview.presentation.ui.main.ask.post.adapter.AskPostImageAdapter
import com.example.koview.presentation.ui.main.ask.post.adapter.AskShopTagAdapter
import com.example.koview.presentation.ui.main.global.createreview.adapter.GalleryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AskPostFragment : BaseFragment<FragmentAskPostBinding>(R.layout.fragment_ask_post),
    AskPostClickListener {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: AskPostViewModel by viewModels()

    private var imageAdapter: AskPostImageAdapter? = null
    private var linkAdapter: AskShopTagAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initEventObserver()
        initStateObserver()
        initImageObserver()
        initAdapter()
    }

    private fun initStateObserver() {
        repeatOnStarted {
            viewModel.imageUiState.collect {
                imageAdapter?.updateImages(it.imageList)
            }
        }

        repeatOnStarted {
            viewModel.linkUiState.collect {
                linkAdapter?.submitList(it.shopLinkList)
            }
        }

        repeatOnStarted {
            viewModel.isButtonEnabled.collect {
                if(it){
                    binding.btnCreate.setBackgroundResource(R.drawable.rect_main3fill_nostroke_12radius)
                } else {
                    binding.btnCreate.setBackgroundResource(R.drawable.rect_blackfill_nostroke_12radius)
                }
                binding.btnCreate.isEnabled = it
            }
        }
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.event.collect {
                when(it){
                    AskPostEvent.GoToGallery -> parentViewModel.goToSetProfileImage()
                    is AskPostEvent.ShowToastMessage -> showToastMessage(it.msg)
                    AskPostEvent.NavigateToBack -> findNavController().navigateUp()
                    AskPostEvent.CreateQuery -> viewModel.postReviewImage(requireContext())
                }
            }
        }
    }


    private fun initImageObserver() {
        repeatOnStarted {
            parentViewModel.imageList.collect {
                viewModel.addImage(it)
            }
        }
    }

    private fun initAdapter() {
        imageAdapter = AskPostImageAdapter(this, emptyList())
        binding.rvImage.adapter = imageAdapter

        linkAdapter = AskShopTagAdapter(this)
        binding.rvShop.adapter = linkAdapter
    }

    // 사진 삭제
    override fun onImageClick(uri: Uri) {
        viewModel.deleteImage(uri)
    }

    // 상품 링크 삭제
    override fun onShopTagClick(item: PurchaseLinkDTO) {
        viewModel.deleteLink(item)
    }
}
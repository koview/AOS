package com.example.koview.presentation.ui.main.ask.post

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.koview.R
import com.example.koview.databinding.FragmentAskPostBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.MainViewModel
import com.example.koview.presentation.ui.main.ask.post.adapter.AskPostClickListener
import com.example.koview.presentation.ui.main.ask.post.adapter.AskPostImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AskPostFragment : BaseFragment<FragmentAskPostBinding>(R.layout.fragment_ask_post),
    AskPostClickListener {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: AskPostViewModel by viewModels()

    private var imageAdapter: AskPostImageAdapter? = null

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
            viewModel.uiState.collect {
                imageAdapter?.submitList(it.imageList)
            }
        }
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.event.collect {
                parentViewModel.goToSetProfileImage()
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
        imageAdapter = AskPostImageAdapter(this)
        binding.rvImage.adapter = imageAdapter
    }

    // 사진 삭제
    override fun onImageClick(uri: Uri) {
        viewModel.deleteImage(uri)
    }
}
package com.example.koview.presentation.ui.main.global.createreview

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.R
import com.example.koview.databinding.FragmentReviewCreateBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.global.createreview.adapter.GalleryAdapter
import com.example.koview.presentation.ui.main.global.createreview.adapter.TagAdapter

class CreateReviewFragment: BaseFragment<FragmentReviewCreateBinding>(R.layout.fragment_review_create) {

    private val viewModel: CreateReviewFragmentViewModel by viewModels()
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
    }

    fun navigateBack() {
        findNavController().navigateUp()
    }
}
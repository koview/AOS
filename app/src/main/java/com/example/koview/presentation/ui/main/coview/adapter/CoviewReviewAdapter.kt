package com.example.koview.presentation.ui.main.coview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.databinding.ItemCoviewBinding
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

interface CoviewClickListener {
    fun onLikeClick(item: CoviewUiData)
    fun onShopTagClick(url: String)
    fun onCommentClick(reviewId: Long, isFullView: Boolean)
}

class CoviewReviewAdapter(private val coviewClickListener: CoviewClickListener) :
    ListAdapter<CoviewUiData, CoviewReviewViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CoviewUiData>() {
            override fun areItemsTheSame(oldItem: CoviewUiData, newItem: CoviewUiData): Boolean {
                return oldItem.reviewId == newItem.reviewId
            }

            override fun areContentsTheSame(oldItem: CoviewUiData, newItem: CoviewUiData): Boolean {
                return oldItem == newItem &&
                        oldItem.currentPage == newItem.currentPage &&
                        oldItem.isExpanded == newItem.isExpanded &&
                        oldItem.isLiked == newItem.isLiked &&
                        oldItem.totalCommentCount == newItem.totalCommentCount
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoviewReviewViewHolder =
        CoviewReviewViewHolder(
            ItemCoviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), coviewClickListener
        )

    override fun onBindViewHolder(holder: CoviewReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CoviewReviewViewHolder(
    private val binding: ItemCoviewBinding,
    private val coviewClickListener: CoviewClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    fun bind(item: CoviewUiData) {
        binding.item = item

        if (item.imageList.isNullOrEmpty()) {
            bindDefaultImage()
        } else {
            setupViewPager(item)
        }

        // 좋아요 업데이트
        binding.layoutLike.setOnClickListener {
            coviewClickListener.onLikeClick(item)
        }

        // 댓글 아이콘 클릭
        binding.layoutCommentInfo.setOnClickListener {
            coviewClickListener.onCommentClick(item.reviewId, false)
        }

        // 댓글 입력창 클릭
        binding.layoutComment.setOnClickListener {
            coviewClickListener.onCommentClick(item.reviewId, true)
        }

        // 리뷰 내용 클릭 리스너 추가
        binding.tvReview.setOnClickListener {
            item.isExpanded = !item.isExpanded
            binding.layoutComment.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
            binding.tvReview.maxLines = if (item.isExpanded) Integer.MAX_VALUE else 2
            binding.rvShop.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
        }

        setupShopLinks(item)
    }

    private fun setupShopLinks(item: CoviewUiData) {
        // 상품 링크 연결
        val context = binding.root.context
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = com.google.android.flexbox.FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.FLEX_START

        binding.rvShop.layoutManager = layoutManager
        binding.rvShop.adapter = CoviewShopTagAdapter(coviewClickListener, item.purchaseLinkList)
    }

    private fun setupViewPager(item: CoviewUiData) {
        // 기존 어댑터와 콜백 해제
        binding.vpImages.adapter?.let {
            binding.vpImages.adapter = null // 기존 어댑터 해제
        }

        // 이전에 등록된 콜백 제거
        pageChangeCallback?.let { binding.vpImages.unregisterOnPageChangeCallback(it) }

        // 리뷰 이미지 ViewPager adapter 설정
        val adapter = CoviewImageVPAdapter(item.imageList)
        binding.vpImages.adapter = adapter

        // 현재 페이지 설정
        binding.vpImages.setCurrentItem(item.currentPage, false)

        // 초기 indicator 설정
        if (item.imageList.size == 1) {
            binding.tvIndicator.visibility = View.GONE
        } else {
            binding.tvIndicator.visibility = View.VISIBLE
            binding.tvIndicator.text = "${item.currentPage + 1} / ${item.imageList.size}"
        }

        // 새 페이지 변경 콜백 등록
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvIndicator.text = "${position + 1} / ${item.imageList.size}"
                item.currentPage = position // 현재 페이지 인덱스 업데이트
            }
        }
        binding.vpImages.registerOnPageChangeCallback(pageChangeCallback!!)
    }

    private fun bindDefaultImage() {
        // 리뷰 이미지 리스트 null 일 때 기본 이미지 설정
        binding.vpImages.visibility = View.GONE
        binding.cvReview.visibility = View.VISIBLE

        Glide.with(binding.root.context)
            .load(R.drawable.img_review_ex)
            .into(binding.ivReview)
    }
}
package com.example.koview.presentation.ui.main.coview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.databinding.ItemCoviewBinding
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData

interface OnLikeClickListener {
    fun onLikeClick(item: CoviewUiData)
}

class CoviewReviewAdapter(private val likeClickListener: OnLikeClickListener) :
    RecyclerView.Adapter<CoviewReviewViewHolder>() {

    private var reviewList: List<CoviewUiData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoviewReviewViewHolder =
        CoviewReviewViewHolder(
            ItemCoviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), likeClickListener
        )

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: CoviewReviewViewHolder, position: Int) {
        holder.bind(reviewList[position])

        if (reviewList[position].imageList.isEmpty()) {
            holder.bindDefaultImage()
        } else {
            holder.setupViewPager(reviewList[position].imageList)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(data: List<CoviewUiData>) {
        reviewList = data
        notifyDataSetChanged()
    }
}

class CoviewReviewViewHolder(
    private val binding: ItemCoviewBinding,
    private val likeClickListener: OnLikeClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CoviewUiData) {
        binding.item = item

        // 좋아요 업데이트
        binding.layoutLike.setOnClickListener {
            likeClickListener.onLikeClick(item)
        }

        // 리뷰 내용 클릭 리스너 추가
        binding.tvReview.setOnClickListener {
            item.isExpanded = !item.isExpanded
            binding.layoutComment.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
            binding.tvReview.maxLines = if (item.isExpanded) Integer.MAX_VALUE else 2
            binding.rvShop.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
        }
    }

    fun setupViewPager(imageList: List<String?>) {

        // 리뷰 이미지 ViewPager adapter 설정
        val adapter = CoviewImageVPAdapter(imageList)
        binding.vpImages.adapter = adapter

        // 초기 indicator 설정
        if (imageList.size == 1) {
            binding.tvIndicator.visibility = View.GONE
        } else {
            binding.tvIndicator.visibility = View.VISIBLE
            binding.tvIndicator.text = "1 / ${imageList.size}"
        }

        // 페이지 변경 콜백 설정
        binding.vpImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvIndicator.text = "${position + 1} / ${imageList.size}"
            }
        })
    }

    fun bindDefaultImage() {
        // 리뷰 이미지 리스트 null 일 때 기본 이미지 설정
        binding.vpImages.visibility = View.GONE
        binding.cvReview.visibility = View.VISIBLE

        Glide.with(binding.root.context)
            .load(R.drawable.img_review_ex)
            .into(binding.ivReview)
    }
}
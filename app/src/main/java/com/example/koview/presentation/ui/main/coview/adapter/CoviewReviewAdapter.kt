package com.example.koview.presentation.ui.main.coview.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    @SuppressLint("NotifyDataSetChanged")
    fun bind(item: CoviewUiData) {
        binding.item = item

        // 리뷰 이미지 ViewPager adapter 설정
        val adapter = CoviewImageVPAdapter(item.imageList)
        binding.vpImages.adapter = adapter

        //Log.d("review", "이미지 전달 | ${item.imageList}")

        // 어댑터의 데이터가 바뀔 때 notifyDataSetChanged 호출
        adapter.notifyDataSetChanged()

        // 좋아요 업데이트
        binding.layoutLike.setOnClickListener {
            likeClickListener.onLikeClick(item)
        }

    }
}
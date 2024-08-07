package com.example.koview.presentation.ui.main.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.ReviewList
import com.example.koview.databinding.ItemReviewBinding

class ReviewsAdapter(private var reviews: List<ReviewList>) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewList) {
            val context = binding.root.context

            binding.review = review

            // 리뷰 이미지 리사이클러뷰 연결
            binding.rvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvImage.adapter = ReviewsImageAdapter(review.imageList)

            binding.executePendingBindings() // 즉시 데이터 바인딩 업데이트
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size

    fun updateReviews(newReviews: List<ReviewList>) {
        reviews = newReviews
        notifyDataSetChanged() // 데이터 변경 알리기
    }
}
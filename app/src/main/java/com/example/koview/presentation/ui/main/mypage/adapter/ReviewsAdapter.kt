package com.example.koview.presentation.ui.main.mypage.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.R
import com.example.koview.data.model.response.MyReview
import com.example.koview.data.model.response.ReviewList
import com.example.koview.databinding.ItemReviewBinding

//item clicklistner를 저장하기 위한 인터페이스
interface MyItemClickListener {
    // 삭제할 리뷰 선택 시작
    fun onLongClick(reviewId: Long)
    // 삭제할 리뷰 선택하기, 리뷰 상세 보기
    fun onItemClick(reviewId: Long)
}

class ReviewsAdapter(private var reviews: List<MyReview>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    private lateinit var reviewImageAdapter: ReviewsImageAdapter
    private lateinit var mItemClickListener: MyItemClickListener //아래 받은 것을 내부에서 사용하기 위해 선언

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])

        val reviewId = reviews[position].reviewId

        // 아이템 클릭 리스너
        holder.itemView.setOnLongClickListener {
            mItemClickListener.onLongClick(reviewId)
            // 해당 아이템만 갱신
            notifyItemChanged(position)
            true
        }
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(reviewId)

            // 해당 아이템만 갱신
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = reviews.size

    fun updateReviews(newReviews: List<MyReview>) {
        reviews = newReviews
        notifyDataSetChanged() // 데이터 변경 알리기
    }

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: MyReview) {
            val context = binding.root.context

            binding.review = review

            // 리뷰 이미지 리사이클러뷰 연결
            binding.rvImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            reviewImageAdapter = ReviewsImageAdapter(review.imageList)
            binding.rvImage.adapter = reviewImageAdapter

            // 선택 상태에 따라 배경색 설정
            binding.root.setBackgroundColor(
                if (review.isSelected) ContextCompat.getColor(
                    context,
                    R.color.kv_main7
                ) else Color.WHITE
            )

            binding.executePendingBindings() // 즉시 데이터 바인딩 업데이트
        }
    }
}
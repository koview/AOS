package com.example.koview.presentation.ui.main.ask.askanswer.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.ReviewList
import com.example.koview.databinding.ItemAskAnswerReviewBinding
import com.example.koview.presentation.ui.main.ask.askanswer.AskAnswerInterface
import com.example.koview.presentation.ui.main.global.product.model.Review

class AskAnswerReviewAdapter(listener: AskAnswerInterface) :
    RecyclerView.Adapter<AskAnswerReviewAdapter.AskAnswerReviewViewHolder>() {

    private val mCallBack = listener
    private var reviewList: List<ReviewList> = emptyList()

    // 현재 선택된 아이템의 포지션을 저장하는 변수 (-1은 선택되지 않음을 의미)
    private var selectedPosition: Int = -1

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<ReviewList>) {
        reviewList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AskAnswerReviewAdapter.AskAnswerReviewViewHolder {
        val binding: ItemAskAnswerReviewBinding = ItemAskAnswerReviewBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return AskAnswerReviewViewHolder(binding, mCallBack, this)
    }

    override fun onBindViewHolder(
        holder: AskAnswerReviewAdapter.AskAnswerReviewViewHolder,
        position: Int
    ) {
        holder.bind(reviewList[position], position, selectedPosition)
    }

    override fun getItemCount(): Int = reviewList.size
    class AskAnswerReviewViewHolder(
        private val binding: ItemAskAnswerReviewBinding,
        private val mCallBack: AskAnswerInterface,
        private val adapter: AskAnswerReviewAdapter
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: ReviewList, position: Int, selectedPosition: Int) {
            val context = binding.root.context

            binding.model = review

            if (position == selectedPosition) {
                binding.rvItem.setBackgroundColor(Color.parseColor("#4D9CC6FF"))
            } else {
                binding.rvItem.setBackgroundColor(Color.WHITE)  // 기본 배경색
            }

            binding.rvItem.setOnLongClickListener {
                // 현재 선택된 아이템의 포지션을 업데이트
                val previousPosition = adapter.selectedPosition
                adapter.selectedPosition = position

                // 이전 선택된 아이템과 현재 선택된 아이템을 새로고침
                adapter.notifyItemChanged(previousPosition)
                adapter.notifyItemChanged(position)

                mCallBack.onReviewLongClick(review.reviewId)
                true
            }

            binding.rvImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvImage.adapter = AskAnswerReviewImageAdapter(review.imageList)
        }
    }
}
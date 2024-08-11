package com.example.koview.presentation.ui.main.ask.askanswer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemAskAnswerReviewBinding
import com.example.koview.presentation.ui.main.global.product.model.Review

class AskAnswerReviewAdapter :
    RecyclerView.Adapter<AskAnswerReviewAdapter.AskAnswerReviewViewHolder>() {

    private var reviewList: List<Review> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<Review>) {
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
        return AskAnswerReviewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AskAnswerReviewAdapter.AskAnswerReviewViewHolder,
        position: Int
    ) {
        holder.bind(reviewList[position])
    }

    override fun getItemCount(): Int = reviewList.size
    class AskAnswerReviewViewHolder(private val binding: ItemAskAnswerReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            val context = binding.root.context

            binding.model = review

            binding.rvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvImage.adapter = AskAnswerReviewImageAdapter(review.imageUrl)
        }
    }
}
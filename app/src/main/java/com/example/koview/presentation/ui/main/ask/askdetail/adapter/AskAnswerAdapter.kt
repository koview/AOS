package com.example.koview.presentation.ui.main.ask.askdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemProductReviewBinding
import com.example.koview.presentation.ui.main.global.product.model.Review
import com.example.koview.presentation.ui.main.global.productdetail.adapter.ProductReviewAdapter
import com.example.koview.presentation.ui.main.global.productdetail.adapter.ProductReviewImageAdapter

class AskAnswerAdapter(private val answerList: List<Review>) : RecyclerView.Adapter<AskAnswerAdapter.AskAnswerViewHolder>(){
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AskAnswerAdapter.AskAnswerViewHolder {
        val binding: ItemProductReviewBinding = ItemProductReviewBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return AskAnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AskAnswerAdapter.AskAnswerViewHolder, position: Int) {
        val answer = answerList[position]
        holder.bind(answer)
    }

    override fun getItemCount(): Int = answerList.size

    class AskAnswerViewHolder(private val binding: ItemProductReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            val context = binding.root.context

            binding.model = review

            binding.rvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvImage.adapter = ProductReviewImageAdapter(review.imageUrl)
        }

    }

}
package com.example.koview.presentation.ui.main.ask.askdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemAskAnswerBinding
import com.example.koview.presentation.ui.main.global.product.model.Review

class AskAnswerAdapter(private val answerList: List<Review>) :
    RecyclerView.Adapter<AskAnswerAdapter.AskAnswerViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AskAnswerAdapter.AskAnswerViewHolder {
        val binding: ItemAskAnswerBinding = ItemAskAnswerBinding.inflate(
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

    class AskAnswerViewHolder(private val binding: ItemAskAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            val context = binding.root.context

            binding.model = review

            binding.rvImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvImage.adapter = AskAnswerImageAdapter(review.imageUrl)
        }

    }

}
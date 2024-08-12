package com.example.koview.presentation.ui.main.ask.askdetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemAskAnswerBinding
import com.example.koview.presentation.ui.main.ask.askdetail.AskDetailInterface
import com.example.koview.presentation.ui.main.ask.model.AskData
import com.example.koview.presentation.ui.main.global.product.model.Review

class AskAnswerAdapter(listener: AskDetailInterface) :
    RecyclerView.Adapter<AskAnswerAdapter.AskAnswerViewHolder>() {

    private var answerList: List<Review> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateReviews(newReviews: List<Review>) {
        answerList = newReviews
        notifyDataSetChanged()
    }

    private val mCallBack = listener
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AskAnswerAdapter.AskAnswerViewHolder {
        val binding: ItemAskAnswerBinding = ItemAskAnswerBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return AskAnswerViewHolder(binding, mCallBack)
    }

    override fun onBindViewHolder(holder: AskAnswerAdapter.AskAnswerViewHolder, position: Int) {
        val answer = answerList[position]
        holder.bind(answer)
    }

    override fun getItemCount(): Int = answerList.size

    class AskAnswerViewHolder(private val binding: ItemAskAnswerBinding, private val mCallBack: AskDetailInterface) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            val context = binding.root.context

            binding.model = review

            binding.layoutLikeIcon.setOnClickListener {
                mCallBack.onClickLike(review)
            }

            binding.rvImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvImage.adapter = AskAnswerImageAdapter(review.imageUrl)
        }

    }

}
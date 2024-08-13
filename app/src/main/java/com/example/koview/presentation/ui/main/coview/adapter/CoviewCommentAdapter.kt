package com.example.koview.presentation.ui.main.coview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.CoviewCommentItem
import com.example.koview.databinding.ItemCoviewCommentBinding

class CoviewCommentAdapter :
    ListAdapter<CoviewCommentItem, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CoviewCommentItem>() {
            override fun areItemsTheSame(
                oldItem: CoviewCommentItem,
                newItem: CoviewCommentItem,
            ): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(
                oldItem: CoviewCommentItem,
                newItem: CoviewCommentItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CoviewCommentViewHolder(
            ItemCoviewCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoviewCommentViewHolder -> holder.bind(getItem(position))
        }
    }
}

class CoviewCommentViewHolder(private val binding: ItemCoviewCommentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CoviewCommentItem) {
        binding.item = item
    }
}

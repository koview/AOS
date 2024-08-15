package com.example.koview.presentation.ui.main.ask.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemLinkBinding
import com.example.koview.presentation.ui.main.ask.model.AskShopUiData

class AskShopTagAdapter(private val clickListener: AskPostClickListener) :
    ListAdapter<AskShopUiData, AskShopTagViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<AskShopUiData>() {
            override fun areItemsTheSame(
                oldItem: AskShopUiData,
                newItem: AskShopUiData,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AskShopUiData,
                newItem: AskShopUiData,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskShopTagViewHolder =
        AskShopTagViewHolder(
            ItemLinkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickListener
        )


    override fun onBindViewHolder(holder: AskShopTagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AskShopTagViewHolder(
    private val binding: ItemLinkBinding,
    private val clickListener: AskPostClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: AskShopUiData) {
        binding.item = tag

        binding.root.setOnClickListener {
            clickListener.onShopTagClick(tag)
        }
    }
}
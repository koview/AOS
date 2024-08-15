package com.example.koview.presentation.ui.main.ask.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.databinding.ItemShopLinkBinding

class AskShopTagAdapter(private val clickListener: AskPostClickListener) :
    ListAdapter<PurchaseLinkDTO, AskShopTagViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PurchaseLinkDTO>() {
            override fun areItemsTheSame(
                oldItem: PurchaseLinkDTO,
                newItem: PurchaseLinkDTO,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PurchaseLinkDTO,
                newItem: PurchaseLinkDTO,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskShopTagViewHolder =
        AskShopTagViewHolder(
            ItemShopLinkBinding.inflate(
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
    private val binding: ItemShopLinkBinding,
    private val clickListener: AskPostClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: PurchaseLinkDTO) {
        binding.item = tag

        binding.root.setOnClickListener {
            clickListener.onShopTagClick(tag)
        }
    }
}
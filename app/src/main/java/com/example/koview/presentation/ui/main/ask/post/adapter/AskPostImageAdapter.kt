package com.example.koview.presentation.ui.main.ask.post.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.databinding.ItemPostImageBinding

interface AskPostClickListener {
    fun onImageClick(uri: Uri)
    fun onShopTagClick(item: PurchaseLinkDTO)
}

class AskPostImageAdapter(private val clickListener: AskPostClickListener) :
    ListAdapter<Uri, AskPostImageViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(
                oldItem: Uri,
                newItem: Uri,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Uri,
                newItem: Uri,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskPostImageViewHolder =
        AskPostImageViewHolder(
            ItemPostImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickListener
        )

    override fun onBindViewHolder(holder: AskPostImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AskPostImageViewHolder(
    private val binding: ItemPostImageBinding,
    private val clickListener: AskPostClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Uri) {
        binding.item = item.toString()

        binding.root.setOnClickListener {
            clickListener.onImageClick(item)
        }
    }
}

package com.example.koview.presentation.ui.main.ask.post.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.databinding.ItemPostImageBinding
import com.example.koview.presentation.ui.main.global.createreview.adapter.GalleryAdapter

interface AskPostClickListener {
    fun onImageClick(uri: Uri)
    fun onShopTagClick(item: PurchaseLinkDTO)
}

class AskPostImageAdapter(private val clickListener: AskPostClickListener, private var imageList: List<Uri>) :
    RecyclerView.Adapter<AskPostImageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskPostImageViewHolder =
        AskPostImageViewHolder(
            ItemPostImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickListener
        )

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: AskPostImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateImages(newLinks: List<Uri>) {
        imageList = newLinks
        notifyDataSetChanged()
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

package com.example.koview.presentation.ui.main.coview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.databinding.ItemCoviewImageBinding

class CoviewImageVPAdapter(private var data: List<String?>) :
    RecyclerView.Adapter<CoviewImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoviewImageViewHolder =
        CoviewImageViewHolder(
            ItemCoviewImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CoviewImageViewHolder, position: Int) {
        if (position < data.size) {
            holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int = data.size
}

class CoviewImageViewHolder(private val binding: ItemCoviewImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String?) {
        Log.d("코뷰", "vp 이미지 : $item")
        Glide.with(binding.root.context)
            .load(item)
            .error(R.drawable.img_review_ex)
            .into(binding.ivReview)
    }
}
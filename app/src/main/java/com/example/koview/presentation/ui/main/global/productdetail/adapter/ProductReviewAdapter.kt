package com.example.koview.presentation.ui.main.global.productdetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.ProductReviewDetail
import com.example.koview.databinding.ItemProductReviewBinding

class ProductReviewAdapter() :
    RecyclerView.Adapter<ProductReviewAdapter.ProductReviewViewHolder>() {

    private var reviewList: List<ProductReviewDetail> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<ProductReviewDetail>) {
        reviewList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ProductReviewViewHolder {
        val binding: ItemProductReviewBinding = ItemProductReviewBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ProductReviewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductReviewViewHolder,
        position: Int
    ) {
        val review = reviewList[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int = reviewList.size

    class ProductReviewViewHolder(private val binding: ItemProductReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: ProductReviewDetail) {
            val context = binding.root.context

            binding.model = review

            binding.rvImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvImage.adapter = ProductReviewImageAdapter(review.imageList)
        }

    }
}
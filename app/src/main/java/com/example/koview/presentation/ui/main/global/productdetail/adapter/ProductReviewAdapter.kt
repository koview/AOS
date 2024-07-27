package com.example.koview.presentation.ui.main.global.productdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemProductReviewBinding
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.example.koview.presentation.ui.main.home.search.model.Review

class ProductReviewAdapter(private val viewModel: SearchViewModel, private val reviewList: List<Review>) :
    RecyclerView.Adapter<ProductReviewAdapter.ProductReviewViewHolder>() {
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

        fun bind(review: Review) {
            val context = binding.root.context

            binding.model = review

            binding.rvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvImage.adapter = ProductReviewImageAdapter(review.imageUrl)
        }

    }
}
package com.example.koview.presentation.ui.main.global.productdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.koview.data.model.response.ProductReviewDetailImageList
import com.example.koview.databinding.ItemProductReviewImageBinding

class ProductReviewImageAdapter(private val reviewImageList: List<ProductReviewDetailImageList>) :
    RecyclerView.Adapter<ProductReviewImageAdapter.ProductReviewImageViewHolder>() {
    override fun onCreateViewHolder(
        viweGroup: ViewGroup,
        viewType: Int
    ): ProductReviewImageViewHolder {
        val binding: ItemProductReviewImageBinding = ItemProductReviewImageBinding.inflate(
            LayoutInflater.from(viweGroup.context),
            viweGroup, false
        )
        return ProductReviewImageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductReviewImageViewHolder,
        position: Int
    ) {
        val reviewImage = reviewImageList[position]
        holder.bind(reviewImage.url)
    }

    override fun getItemCount(): Int = reviewImageList.size

    class ProductReviewImageViewHolder(private val binding: ItemProductReviewImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewImage: String) {
            // ImageView의 크기가 결정된 후에 Glide로 이미지 로드
            // ImageView의 크기를 얻음
            binding.ivReview.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        binding.ivReview.viewTreeObserver.removeOnPreDrawListener(this)
                        val width = binding.ivReview.width
                        val height = binding.ivReview.height

                        val requestOptions =
                            RequestOptions().transform(CenterCrop(), RoundedCorners(16))

                        Glide.with(itemView)
                            .load(reviewImage)
                            .apply(requestOptions)
                            .override(width, height)
                            .into(binding.ivReview)
                        // 이 부분은 피그마에서 추가적으로 디자인되면 하면 좋을 듯
                        // placeholder: 이미지 로딩을 시작하기 전에 보여줄 이미지 설정
                        // error: 리소스를 불러오다가 에러가 발생했을 때 보여줄 이미지 설정
//                            .placeholder(R.drawable.placeholder_image)
//                            .error(R.drawable.error_image)

                        return true
                    }
                }
            )
        }
    }
}
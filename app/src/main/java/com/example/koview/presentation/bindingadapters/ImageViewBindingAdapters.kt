package com.example.koview.presentation.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.koview.R

@BindingAdapter("profileImgUrl")
fun bindProfileImg(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.img_profile_ex)
        .into(imageView)
}

@BindingAdapter("askImgUrl")
fun bindAskImg(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.img_product_ex)
        .into(imageView)
}
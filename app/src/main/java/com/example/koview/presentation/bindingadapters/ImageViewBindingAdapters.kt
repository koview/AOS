package com.example.koview.presentation.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.koview.R
import com.example.koview.presentation.ui.main.home.model.Category

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

@BindingAdapter("targetCategory", "curCategory", requireAll = true)
fun setCategoryColor(view: ImageView, targetCategory: Category, curCategory: Category) {
    val context = view.context
    val main3Color = ContextCompat.getColor(context, R.color.kv_main3)
    val defaultColor = ContextCompat.getColor(context, R.color.black)

    // targetCategory : 비교 대상
    // curCategory : 현재 선택된 카테고리
    if (targetCategory == curCategory) {
        view.setColorFilter(main3Color)
    } else {
        view.setColorFilter(defaultColor)
    }
}


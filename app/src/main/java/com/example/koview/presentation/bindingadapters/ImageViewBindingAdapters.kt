package com.example.koview.presentation.bindingadapters

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.koview.R
import com.example.koview.presentation.ui.main.home.model.Category

@BindingAdapter("profileImgUrl")
fun bindProfileImg(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url ?: R.drawable.img_profile_ex)
        .error(R.drawable.img_profile_ex)
        .into(imageView)
}

@BindingAdapter("askImgUrl")
fun bindAskImg(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.img_review_ex)
        .into(imageView)
}

@BindingAdapter("productImgUrl")
fun bindProductImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url ?: R.drawable.default_product_image)
        .error(R.drawable.img_product_ex_2)
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

@BindingAdapter("setCategoryButtonColor")
fun setCategoryColor(appCompatImageButton: AppCompatImageButton, curCategory: Category) {
    val context = appCompatImageButton.context
    val main3Color = ContextCompat.getColor(context, R.color.kv_main3)
    val defaultColor = ContextCompat.getColor(context, R.color.kv_gray4)
    if (curCategory != Category.ALL) {
        appCompatImageButton.setColorFilter(main3Color)
    } else {
        appCompatImageButton.setColorFilter(defaultColor)
    }
}

@BindingAdapter("searchProductImgUrl")
fun bindSearchProductImg(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(R.drawable.default_product_image)
        .error(R.drawable.default_product_image)
        .transform(CenterCrop(), RoundedCorners(36))
        .into(imageView)
}
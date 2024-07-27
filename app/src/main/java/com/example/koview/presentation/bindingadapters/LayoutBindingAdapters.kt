package com.example.koview.presentation.bindingadapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.koview.presentation.ui.main.home.search.model.Review

@BindingAdapter("reviewLayoutVisibility")
fun bindReviewLayoutVisibility(layout: LinearLayout, reviewList: List<Review>) {
    layout.visibility = if (reviewList.isEmpty()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("tagShopLayoutColor")
fun bindTagShopLayoutColor(layout: ConstraintLayout, isVerify: Boolean) {
    if (isVerify) {
        layout.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F60000"))
    }

}
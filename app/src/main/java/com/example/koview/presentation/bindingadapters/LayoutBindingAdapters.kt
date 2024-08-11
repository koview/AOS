package com.example.koview.presentation.bindingadapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.koview.data.model.response.Status
import com.example.koview.data.model.response.VerifiedType

@BindingAdapter("reviewLayoutVisibility")
fun bindReviewLayoutVisibility(layout: LinearLayout, reviewCount: Long) {
    layout.visibility = if (reviewCount == 0.toLong()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("tagShopLayoutColor")
fun bindTagShopLayoutColor(layout: ConstraintLayout, verifiedType: VerifiedType) {
    if (verifiedType == VerifiedType.UNVERIFIED) {
        layout.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F60000"))
    }
}

@BindingAdapter("FamousIconLayoutVisibility")
fun bindFamousIconLayoutVisibility(layout: ConstraintLayout, status: Status) {
    layout.visibility = if (status == Status.FAMOUS) View.VISIBLE else View.GONE
}

@BindingAdapter("RestrictedIconLayoutVisibility")
fun bindRestrictedIconLayoutVisibility(layout: ConstraintLayout, status: Status) {
    layout.visibility = if (status == Status.RESTRICTED) View.VISIBLE else View.GONE
}


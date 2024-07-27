package com.example.koview.presentation.bindingadapters

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.example.koview.presentation.ui.main.home.search.model.Review

@BindingAdapter("linearLayoutVisibility")
fun bindLinearLayoutVisibility(linearLayout: LinearLayout, reviewList: List<Review>) {
    linearLayout.visibility = if (reviewList.isEmpty()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
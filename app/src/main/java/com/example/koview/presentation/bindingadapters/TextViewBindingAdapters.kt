package com.example.koview.presentation.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.koview.R
import com.example.koview.data.model.response.Status
import com.example.koview.presentation.ui.main.home.model.Category

@BindingAdapter("textFromInt")
fun TextView.bindTextFromInt(value: Int) {
    text = value.toString()
}

@BindingAdapter("textFromLong")
fun TextView.bindTextFromLong(value: Long) {
    text = value.toString()
}


@BindingAdapter("targetCategory", "curCategory", requireAll = true)
fun setCategoryColor(view: TextView, targetCategory: Category, curCategory: Category) {
    val context = view.context
    val main3Color = ContextCompat.getColor(context, R.color.kv_main3)
    val defaultColor = ContextCompat.getColor(context, R.color.black)

    // targetCategory : 비교 대상
    // curCategory : 현재 선택된 카테고리
    if (targetCategory == curCategory) {
        view.setTextColor(main3Color)
    } else {
        view.setTextColor(defaultColor)
    }
}

@BindingAdapter("NormalTextviewVisibility")
fun bindNormalIconLayoutVisibility(view: TextView, status: Status) {
    view.visibility = if (status == Status.NORMAL) View.VISIBLE else View.GONE
}
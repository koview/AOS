package com.example.koview.presentation.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textFromInt")
fun TextView.bindTextFromInt(value: Int) {
    text = value.toString()
}
package com.example.koview.presentation.ui.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemHomeAskBinding
import com.example.koview.presentation.ui.main.home.model.AskUiData

class HomeAskAdapter() : RecyclerView.Adapter<AskViewHolder>() {
    // todo : 질문 게시글 화면으로 연결

    private var askList: List<AskUiData> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<AskUiData>) {
        askList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskViewHolder {
        val binding: ItemHomeAskBinding =
            ItemHomeAskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AskViewHolder, position: Int) {
        holder.bind(askList[position])
    }

    override fun getItemCount(): Int = askList.size
}

class AskViewHolder(val binding: ItemHomeAskBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(askList: AskUiData) {
        binding.item = askList
    }
}
package com.example.koview.presentation.ui.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemHomeAskBinding
import com.example.koview.presentation.ui.main.home.model.AskUiData

interface HomeClickListener {
    fun navigateToAsk()
}

class HomeAskAdapter(private val homeClickListener: HomeClickListener) :
    RecyclerView.Adapter<AskViewHolder>() {
    // todo : 질문 게시글 화면으로 연결

    private var askList: List<AskUiData> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<AskUiData>) {
        askList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskViewHolder =
        AskViewHolder(
            ItemHomeAskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), homeClickListener
        )

    override fun onBindViewHolder(holder: AskViewHolder, position: Int) {
        holder.bind(askList[position])
    }

    override fun getItemCount(): Int = askList.size
}

class AskViewHolder(
    val binding: ItemHomeAskBinding,
    private val homeClickListener: HomeClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(askList: AskUiData) {
        binding.item = askList

        // 질문하기 화면으로 이동
        binding.root.setOnClickListener() {
            homeClickListener.navigateToAsk()
        }
    }
}
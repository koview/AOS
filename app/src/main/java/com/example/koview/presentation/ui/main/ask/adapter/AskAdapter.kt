package com.example.koview.presentation.ui.main.ask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemAskBinding
import com.example.koview.presentation.bindingadapters.bindAskImg
import com.example.koview.presentation.ui.main.ask.model.AskData
import com.example.koview.presentation.ui.main.global.product.model.Product

class AskAdapter : RecyclerView.Adapter<AskAdapter.AskViewHolder>() {

    private var askList: List<AskData> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<AskData>) {
        askList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AskViewHolder {
        val binding: ItemAskBinding = ItemAskBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        return AskViewHolder(binding)
    }

    override fun getItemCount(): Int = askList.size

    override fun onBindViewHolder(holder: AskViewHolder, position: Int) {
        holder.bind(askList[position])
    }

    class AskViewHolder(private val binding: ItemAskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ask: AskData) {
            binding.model = ask
        }
    }
}
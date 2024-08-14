package com.example.koview.presentation.ui.main.ask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.QueryResultList
import com.example.koview.databinding.ItemAskBinding
import com.example.koview.presentation.ui.main.ask.AskInterface
import com.example.koview.presentation.ui.main.ask.model.AskData

class AskAdapter(listener: AskInterface) : RecyclerView.Adapter<AskAdapter.AskViewHolder>() {

    private val mCallBack = listener
    private var askList: List<QueryResultList> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<QueryResultList>) {
        askList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AskViewHolder {
        val binding: ItemAskBinding = ItemAskBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        return AskViewHolder(binding, mCallBack)
    }

    override fun getItemCount(): Int = askList.size

    override fun onBindViewHolder(holder: AskViewHolder, position: Int) {
        holder.bind(askList[position])
    }

    class AskViewHolder(private val binding: ItemAskBinding, private val mCallBack: AskInterface) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ask: QueryResultList) {
            binding.model = ask

            binding.layoutAsk.setOnClickListener {
                mCallBack.onAskClick(ask)
            }

            binding.layoutAskIcon.setOnClickListener {
                mCallBack.onAskIconClick(ask)
            }
        }
    }
}
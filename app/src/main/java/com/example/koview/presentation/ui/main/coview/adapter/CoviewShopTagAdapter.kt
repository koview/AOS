package com.example.koview.presentation.ui.main.coview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.PurchaseLinkList
import com.example.koview.databinding.ItemProductShopTagBinding

class CoviewShopTagAdapter(
    private val clickListener: CoviewClickListener,
    private val tagList: List<PurchaseLinkList>,
) : RecyclerView.Adapter<CoviewShopTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoviewShopTagViewHolder =
        CoviewShopTagViewHolder(
            ItemProductShopTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickListener
        )

    override fun getItemCount(): Int = tagList.size

    override fun onBindViewHolder(holder: CoviewShopTagViewHolder, position: Int) {
        holder.bind(tagList[position])
    }
}

class CoviewShopTagViewHolder(
    private val binding: ItemProductShopTagBinding,
    private val listener: CoviewClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tagShop: PurchaseLinkList) {
        binding.model = tagShop

        binding.layoutTag.setOnClickListener {
            listener.onShopTagClick(tagShop.purchaseUrl)
        }
    }
}
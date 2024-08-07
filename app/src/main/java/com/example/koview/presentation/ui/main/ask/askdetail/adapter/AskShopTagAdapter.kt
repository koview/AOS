package com.example.koview.presentation.ui.main.ask.askdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemProductShopTagBinding
import com.example.koview.presentation.ui.main.ask.askdetail.AskDetailInterface
import com.example.koview.presentation.ui.main.global.product.adapter.ProductShopTagAdapter
import com.example.koview.presentation.ui.main.global.product.model.TagShop

class AskShopTagAdapter(listener: AskDetailInterface, private val tagList: List<TagShop>) :
    RecyclerView.Adapter<AskShopTagAdapter.AskShopTagViewHolder>() {

    private val mCallBack = listener
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AskShopTagAdapter.AskShopTagViewHolder {
        val binding: ItemProductShopTagBinding = ItemProductShopTagBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return AskShopTagViewHolder(binding, mCallBack)
    }

    override fun onBindViewHolder(holder: AskShopTagAdapter.AskShopTagViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int = tagList.size

    class AskShopTagViewHolder(
        private val binding: ItemProductShopTagBinding,
        private val mCallBack: AskDetailInterface
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tagShop: TagShop) {
            binding.model = tagShop

            binding.layoutTag.setOnClickListener {
                mCallBack.onClickTag(tagShop.productUrl)
            }
        }
    }
}
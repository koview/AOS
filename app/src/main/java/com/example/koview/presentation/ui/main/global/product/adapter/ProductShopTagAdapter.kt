package com.example.koview.presentation.ui.main.global.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.PurchaseLinkList
import com.example.koview.databinding.ItemProductShopTagBinding
import com.example.koview.presentation.ui.main.global.product.ProductInterface
import com.example.koview.presentation.ui.main.global.product.model.TagShop

class ProductShopTagAdapter(
    listener: ProductInterface,
    private val tagList: List<PurchaseLinkList>
) :
    RecyclerView.Adapter<ProductShopTagAdapter.SearchShopViewHolder>() {

    private val mCallBack = listener
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchShopViewHolder {
        val binding: ItemProductShopTagBinding = ItemProductShopTagBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return SearchShopViewHolder(binding, mCallBack)
    }

    override fun onBindViewHolder(holder: SearchShopViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int = tagList.size

    class SearchShopViewHolder(
        private val binding: ItemProductShopTagBinding,
        private val mCallBack: ProductInterface
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tagShop: PurchaseLinkList) {
            binding.model = tagShop

            binding.layoutTag.setOnClickListener {
                mCallBack.onProductShopTagClick(tagShop.purchaseUrl)
            }
        }
    }
}
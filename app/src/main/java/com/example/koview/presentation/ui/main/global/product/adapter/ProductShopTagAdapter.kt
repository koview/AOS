package com.example.koview.presentation.ui.main.global.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemProductShopTagBinding
import com.example.koview.presentation.ui.main.global.product.ProductViewModel
import com.example.koview.presentation.ui.main.home.search.model.TagShop

class ProductShopTagAdapter(
    private val viewModel: ProductViewModel,
    private val tagList: List<TagShop>
) :
    RecyclerView.Adapter<ProductShopTagAdapter.SearchShopViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchShopViewHolder {
        val binding: ItemProductShopTagBinding = ItemProductShopTagBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return SearchShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchShopViewHolder, position: Int) {
        holder.bind(viewModel, tagList[position])
    }

    override fun getItemCount(): Int = tagList.size

    class SearchShopViewHolder(private val binding: ItemProductShopTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ProductViewModel, tagShop: TagShop) {
            binding.model = tagShop
            binding.productVm = viewModel
        }

    }
}
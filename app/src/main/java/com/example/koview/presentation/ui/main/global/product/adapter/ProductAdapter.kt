package com.example.koview.presentation.ui.main.global.product.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemProductBinding
import com.example.koview.presentation.ui.main.global.ProductViewModel
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class ProductAdapter(private val viewModel: ProductViewModel) :
    RecyclerView.Adapter<ProductAdapter.SearchProductViewHolder>() {

    private var productList: List<SearchProduct> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<SearchProduct>) {
        productList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchProductViewHolder {
        val binding: ItemProductBinding = ItemProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return SearchProductViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(
        holder: SearchProductViewHolder,
        position: Int
    ) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
    class SearchProductViewHolder(
        private val binding: ItemProductBinding,
        private val viewModel: ProductViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchProduct: SearchProduct) {
            binding.model = searchProduct
            binding.productVm = viewModel
            val context = binding.root.context

            // FlexboxLayoutManager 설정
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = com.google.android.flexbox.FlexWrap.WRAP
            layoutManager.justifyContent = JustifyContent.FLEX_START

            binding.rvShop.layoutManager = layoutManager
            binding.rvShop.adapter = ProductShopTagAdapter(viewModel, searchProduct.shopList)
        }

    }
}
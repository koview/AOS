package com.example.koview.presentation.ui.main.global.product.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemProductBinding
import com.example.koview.presentation.ui.main.global.product.ProductInterface
import com.example.koview.presentation.ui.main.global.product.model.Product
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class ProductAdapter(listener: ProductInterface) :
    RecyclerView.Adapter<ProductAdapter.SearchProductViewHolder>() {

    private val mCallBack = listener
    private var productList: List<Product> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<Product>) {
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
        return SearchProductViewHolder(binding, mCallBack)
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
        private val mCallBack: ProductInterface
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchProduct: Product) {
            binding.model = searchProduct
            val context = binding.root.context

            // FlexboxLayoutManager 설정
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = com.google.android.flexbox.FlexWrap.WRAP
            layoutManager.justifyContent = JustifyContent.FLEX_START

            binding.rvShop.layoutManager = layoutManager
            binding.rvShop.adapter = ProductShopTagAdapter(mCallBack, searchProduct.shopList)

            binding.layoutProduct.setOnClickListener {
                mCallBack.onProductClick(searchProduct)
            }
        }
    }
}
package com.example.koview.presentation.ui.main.home.search.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemSearchShopBinding
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.example.koview.presentation.ui.main.home.search.model.TagShop

class SearchShopAdapter(
    private val viewModel: SearchViewModel,
    private val tagList: List<TagShop>
) :
    RecyclerView.Adapter<SearchShopAdapter.SearchShopViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchShopAdapter.SearchShopViewHolder {
        val binding: ItemSearchShopBinding = ItemSearchShopBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return SearchShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchShopAdapter.SearchShopViewHolder, position: Int) {
        holder.bind(viewModel, tagList[position])
    }

    override fun getItemCount(): Int = tagList.size

    class SearchShopViewHolder(private val binding: ItemSearchShopBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: SearchViewModel, tagShop: TagShop) {
            binding.model = tagShop
            binding.vm = viewModel
        }

    }
}
package com.example.koview.presentation.ui.main.global.createreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.data.model.response.MyReview
import com.example.koview.databinding.ItemLinkBinding

class TagAdapter(private var linkList: List<PurchaseLinkDTO>) :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    interface MyItemClickListener{
        fun onDeleteClick(link: PurchaseLinkDTO)
    }
    private lateinit var mItemClickListener: MyItemClickListener //아래 받은 것을 내부에서 사용하기 위해 선언
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListener = itemClickListener
    }

    inner class TagViewHolder(private val binding: ItemLinkBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(link: PurchaseLinkDTO) {
            binding.tvTag.text = link.shopName  // 추출한 태그를 tvTag에 설정

            // ivX 클릭 리스너 설정
            binding.ivX.setOnClickListener {
                // 클릭 시 mItemClickListener의 onDeleteClick 호출
                if (::mItemClickListener.isInitialized) {
                    mItemClickListener.onDeleteClick(link)
                }
            }

            binding.executePendingBindings() // 즉시 데이터 바인딩 업데이트
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLinkBinding.inflate(inflater, parent, false)
        return TagViewHolder(binding)
    }

    override fun getItemCount(): Int = linkList.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val link = linkList[position]
        holder.bind(link)


    }

    fun updateLinks(newLinks: List<PurchaseLinkDTO>) {
        linkList = newLinks
        notifyDataSetChanged() // 데이터 변경 알리기
    }
}
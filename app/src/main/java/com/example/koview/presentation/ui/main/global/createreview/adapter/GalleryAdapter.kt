package com.example.koview.presentation.ui.main.global.createreview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.koview.data.model.requeset.PurchaseLinkDTO
import com.example.koview.databinding.ItemGalleryBinding

class GalleryAdapter(private var imageList: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){

    interface MyItemClickListener{
        fun onDeleteClick(imageUrl: String)
    }
    private lateinit var mItemClickListener: MyItemClickListener //아래 받은 것을 내부에서 사용하기 위해 선언
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListener = itemClickListener
    }

    inner class GalleryViewHolder(private val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(image: String){
            // ImageView의 크기가 결정된 후에 Glide로 이미지 로드
            // ImageView의 크기를 얻음
            binding.ivImage.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        binding.ivImage.viewTreeObserver.removeOnPreDrawListener(this)
                        val width = binding.ivImage.width
                        val height = binding.ivImage.height

                        val requestOptions =
                            RequestOptions().transform(CenterCrop(), RoundedCorners(5))

                        Glide.with(itemView)
                            .load(image)
                            .apply(requestOptions)
                            .override(width, height)
                            .into(binding.ivImage)
                        return true
                    }
                }
            )

            // delete 버튼 설정
            binding.ivDelete.setOnClickListener {
                // 클릭 시 mItemClickListener의 onDeleteClick 호출
                if (::mItemClickListener.isInitialized) {
                    mItemClickListener.onDeleteClick(image)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding: ItemGalleryBinding = ItemGalleryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image = imageList[position]
        holder.bind(image)
    }

    fun updateImages(newLinks: List<String>) {
        imageList = newLinks
        notifyDataSetChanged() // 데이터 변경 알리기
    }
}
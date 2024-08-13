package com.example.koview.presentation.ui.main.global.createreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.koview.databinding.ItemGalleryBinding

class GalleryAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){

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
                        // 이 부분은 피그마에서 추가적으로 디자인되면 하면 좋을 듯
                        // placeholder: 이미지 로딩을 시작하기 전에 보여줄 이미지 설정
                        // error: 리소스를 불러오다가 에러가 발생했을 때 보여줄 이미지 설정
//                            .placeholder(R.drawable.placeholder_image)
//                            .error(R.drawable.error_image)

                        return true
                    }
                }
            )
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
}
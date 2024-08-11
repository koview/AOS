package com.example.koview.presentation.ui.main.mypage.setting.point.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.data.model.response.PointDetail
import com.example.koview.databinding.ItemPointsBinding

class PointDetailRVAdapter(private var pointList: List<PointDetail>):
    RecyclerView.Adapter<PointDetailRVAdapter.PointViewHolder>() {
    inner class PointViewHolder(private val binding: ItemPointsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(point: PointDetail){
            binding.point = point

            binding.executePendingBindings() // 즉시 데이터 바인딩 업데이트
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPointsBinding.inflate(inflater, parent, false)
        return PointViewHolder(binding)
    }

    override fun getItemCount(): Int = pointList.size

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(pointList[position])
    }
}
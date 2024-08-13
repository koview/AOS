package com.example.koview.presentation.ui.main.global.createreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koview.databinding.ItemLinkBinding

class TagAdapter(private val linkList: List<String>) :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {
    inner class TagViewHolder(private val binding: ItemLinkBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(link: String) {
            val tag = extractTag(link)
            binding.tvTag.text = tag  // 추출한 태그를 tvTag에 설정
        }

        // URL로부터 도메인 이름 추출하는 함수
        private fun extractTag(link: String): String {
            // 정규 표현식을 사용하여 www.과 .com 사이의 문자열을 검색
            val regex = """https?://(www\.)?([^/]+)\.com(/.*)?""".toRegex()
            val matchResult = regex.find(link)

            return if (matchResult != null) {
                matchResult.groups[2]?.value ?: ""  // 그룹 2에서 도메인 이름 반환
            } else {
                ""  // 매치되지 않는 경우 빈 문자열 반환
            }
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
}
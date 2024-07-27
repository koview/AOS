package com.example.koview.presentation.ui.main.home.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.koview.R
import com.example.koview.databinding.ItemSearchProductBinding
import com.example.koview.presentation.ui.main.home.search.SearchViewModel
import com.example.koview.presentation.ui.main.home.search.model.Review
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct
import com.example.koview.presentation.ui.main.home.search.model.TagShop
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class SearchProductAdapter(private val viewModel: SearchViewModel) :
    RecyclerView.Adapter<SearchProductAdapter.SearchProductViewHolder>() {

    // 더미데이터 -> api연동 후 삭제
    val productList = listOf(
        SearchProduct(
            title = "얏호",
            imageUrl = "",
            reviewNumber = 1,
            registDate = "2024-07-12",
            isWarning = true,
            isHot = false,
            shopList = listOf(
                TagShop(title = "xpadfnejnnddf", isVerify = false),
                TagShop(title = "xpadfnejnnddf", isVerify = true),
                TagShop(title = "xpadfnejnnddfdfsdfsdf", isVerify = true),
                TagShop(title = "Shop 1", isVerify = false),
                TagShop(title = "Shop 2", isVerify = true),
                TagShop(title = "Shop 3", isVerify = true),
                TagShop(title = "Shop 4", isVerify = true),
                TagShop(title = "Shop 5", isVerify = true),
                TagShop(title = "Shop 6", isVerify = true),
                TagShop(title = "Shop B", isVerify = true),
                TagShop(title = "Shop A", isVerify = true),
                TagShop(title = "Shop B", isVerify = true)
            ),
            reviewList = listOf(
//                Review(
//                    nickname = "네로",
//                    content = "테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명테스트설명",
//                    imageUrl = listOf(
//                        "https://ifh.cc/g/f9WcP4.jpg",
//                        "https://ifh.cc/g/f9WcP4.jpg"
//                    ),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                ),
//                Review(
//                    nickname = "ddddd",
//                    content = "설명입니당",
//                    imageUrl = listOf(
//                        "https://ifh.cc/g/f9WcP4.jpg",
//                    ),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                ),
//                Review(
//                    nickname = "2323",
//                    content = "설명입니당",
//                    imageUrl = listOf(),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                ),
//                Review(
//                    nickname = "sssss",
//                    content = "설명입니당",
//                    imageUrl = listOf(
//                        "https://ifh.cc/g/f9WcP4.jpg",
//                        "https://ifh.cc/g/f9WcP4.jpg"
//                    ),
//                    likeNumber = 10,
//                    commentNumber = 20,
//                    date = "2024-07-13"
//                )
            )
        ),
        SearchProduct(
            title = "어라어라얼",
            imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
            reviewNumber = 5,
            registDate = "2024-07-13",
            isWarning = false,
            isHot = true,
            shopList = listOf(

            ),
            reviewList = listOf(
                Review(
                    nickname = "커너",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "ddddd",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "2323",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "sssss",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg",
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                )
            )
        ),
        SearchProduct(
            title = "dd",
            imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
            reviewNumber = 5,
            registDate = "2024-07-13",
            isWarning = false,
            isHot = false,
            shopList = listOf(
                TagShop(title = "Shop C", isVerify = true),
                TagShop(title = "Shop D", isVerify = true),
                TagShop(title = "Shop d", isVerify = true)
            ),
            reviewList = listOf(
                Review(
                    nickname = "멜리",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "ddddd",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg",
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "2323",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg",
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "sssss",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                )
            )
        ),
        SearchProduct(
            title = "dd",
            imageUrl = "https://ifh.cc/g/f9WcP4.jpg",
            reviewNumber = 5,
            registDate = "2024-07-13",
            isWarning = true,
            isHot = false,
            shopList = listOf(
                TagShop(title = "Shop C", isVerify = false),
                TagShop(title = "Shop D", isVerify = false),
                TagShop(title = "Shop d", isVerify = false)
            ),
            reviewList = listOf(
                Review(
                    nickname = "네로",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "ddddd",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "2323",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                ),
                Review(
                    nickname = "sssss",
                    content = "설명입니당",
                    imageUrl = listOf(
                        "https://ifh.cc/g/f9WcP4.jpg"
                    ),
                    likeNumber = 10,
                    commentNumber = 20,
                    date = "2024-07-13"
                )
            )
        )
    )

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchProductAdapter.SearchProductViewHolder {
        val binding: ItemSearchProductBinding = ItemSearchProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return SearchProductViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(
        holder: SearchProductAdapter.SearchProductViewHolder,
        position: Int
    ) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size
    class SearchProductViewHolder(
        private val binding: ItemSearchProductBinding,
        private val viewModel: SearchViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchProduct: SearchProduct) {
            binding.model = searchProduct
            binding.vm = viewModel
            val context = binding.root.context

            // ImageView의 크기가 결정된 후에 Glide로 이미지 로드
            // ImageView의 크기를 얻음
            binding.ivProduct.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        binding.ivProduct.viewTreeObserver.removeOnPreDrawListener(this)
                        val width = binding.ivProduct.width
                        val height = binding.ivProduct.height

                        val requestOptions =
                            RequestOptions().transform(CenterCrop(), RoundedCorners(16))

                        Glide.with(itemView)
                            .load(searchProduct.imageUrl)
                            .placeholder(R.drawable.default_product_image)
                            .error(R.drawable.default_product_image)
                            .apply(requestOptions)
                            .override(width, height)
                            .into(binding.ivProduct)

                        return true
                    }
                }
            )

            // FlexboxLayoutManager 설정
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = com.google.android.flexbox.FlexWrap.WRAP
            layoutManager.justifyContent = JustifyContent.FLEX_START

            binding.rvShop.layoutManager = layoutManager
            val adapter = SearchShopAdapter(searchProduct.shopList)
            binding.rvShop.adapter = adapter
        }

    }
}
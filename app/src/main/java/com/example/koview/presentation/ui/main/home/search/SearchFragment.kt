package com.example.koview.presentation.ui.main.home.search

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koview.R
import com.example.koview.databinding.FragmentSearchBinding
import com.example.koview.presentation.base.BaseFragment
import com.example.koview.presentation.ui.main.home.search.adapter.SearchProductAdapter
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct
import com.example.koview.presentation.ui.main.home.search.model.TagShop

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initSearchProductRecyclerview()
        initEventObserve()
        enterSearch()
    }

    private fun initSearchProductRecyclerview() {
        val adapter = SearchProductAdapter(viewModel)
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = adapter
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect { event ->
                when(event) {
                    is SearchEvent.NavigateToProductDetail -> {
                        viewModel.searchProduct.value?.let { searchProduct ->
                            findNavController().toProductDetail(searchProduct)
                        }
                    }
                }
            }
        }
    }

    private fun NavController.toProductDetail(searchProduct: SearchProduct) {
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(searchProduct)
        navigate(action)
    }

    private fun enterSearch() {
        binding.etSearch.setOnEditorActionListener { textView, actionId, event ->
            var handled = false
            // 확인 버튼 눌렀을 때
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // TODO: 여기서 ViewModel 함수 호출
                Log.d("엔터", "엔터")
                // 키보드 내려가기
                val inputMethodManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                handled = true
            }
            handled
        }
    }

}
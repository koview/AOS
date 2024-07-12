package com.example.koview.presentation.ui.main.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koview.presentation.ui.main.home.search.model.SearchProduct

class SearchViewModel : ViewModel() {

    private var _searchProductList = MutableLiveData<List<SearchProduct>>()
    val searchProductList : LiveData<List<SearchProduct>> get() = _searchProductList
}
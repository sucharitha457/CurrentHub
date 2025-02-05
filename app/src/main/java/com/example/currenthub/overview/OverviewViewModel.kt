package com.example.currenthub.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currenthub.network.Article

class OverviewViewModel : ViewModel() {
    val OutputResult = MutableLiveData<List<Article>>()
    private var paginationManager: PaginationManager? = null

    fun initializePagination(apikey: String, query: String) {
        paginationManager = PaginationManager(apikey, query) { newArticles ->
            val currentList = OutputResult.value.orEmpty()
            Log.d("OverviewVM", "Loaded articles: ${newArticles.size}")
            OutputResult.postValue(currentList + newArticles)
        }
        paginationManager?.loadFirstPage(viewModelScope)
    }

    fun loadMoreData() {
        paginationManager?.loadNextPage(viewModelScope)
    }

    val navigateToContent = MutableLiveData<String?>()

    fun onNavigatedToContent() {
        navigateToContent.value = null
    }

    fun onDetailClicked(newsId: String) {
        navigateToContent.value = newsId
    }
}

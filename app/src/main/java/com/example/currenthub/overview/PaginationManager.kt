package com.example.currenthub.overview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currenthub.network.Article
import com.example.currenthub.network.NewsApi
import com.example.currenthub.network.NextPageApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PaginationManager(
    private val apikey: String,
    private val query: String,
    private val onPageLoaded: (List<Article>) -> Unit
) {
    private var pageId: String? = null
    private var isLoading = false
    val hasNextPage = MutableLiveData(true)

    fun loadFirstPage(scope: CoroutineScope) {
        resetPagination()
        fetchPage(scope)
    }

    fun loadNextPage(scope: CoroutineScope) {
        if (!isLoading && hasNextPage.value == true && !pageId.isNullOrEmpty()) {
            fetchPage(scope)
        }
    }

    private fun fetchPage(scope: CoroutineScope) {
        isLoading = true
        scope.launch {
            try {
                val response = if (pageId.isNullOrEmpty()) {
                    NewsApi.NewsretrofitService.getNews(apikey, query).await()
                } else {
                    NextPageApi.NewsNextPageretrofitService.getNews(apikey, query, pageId!!).await()
                }

                response.results?.let { articles ->
                    onPageLoaded(articles)
                }
                pageId = response.nextPage
                hasNextPage.postValue(!response.nextPage.isNullOrEmpty())
            } catch (e: Exception) {
                Log.e("PaginationManager", "Error fetching page: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    private fun resetPagination() {
        pageId = null
        hasNextPage.postValue(true)
    }
}

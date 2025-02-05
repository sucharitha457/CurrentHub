package com.example.currenthub.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currenthub.network.NewsApi
import com.example.currenthub.network.NewsApiById
import com.example.currenthub.network.NewsApiResponse
import kotlinx.coroutines.launch

class DetailViewModel:ViewModel() {
    var newsResponce = MutableLiveData<NewsApiResponse?>()
    fun getNewsResponce(apikey:String,id:String){
        viewModelScope.launch {
            try {
                newsResponce.value = NewsApiById.NewsWithIdretrofitService.getNews(apikey,id).await()
                Log.d("","........,,,${newsResponce.value}")
            } catch (e: Exception) {
                Log.e("DisplayViewModel", "Error fetching song: ${e.message}")
            }
        }
    }
}
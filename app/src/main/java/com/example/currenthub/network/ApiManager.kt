package com.example.currenthub.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val NEWS_BASE_URL = "https://newsdata.io/api/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val weatherretrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(WEATHER_BASE_URL)
    .build()

private val newsretrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(NEWS_BASE_URL)
    .build()

interface WeatherApiService {
    @GET("weather")
    fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") currentWeather: String
    ): Deferred<WeatherApiResponse>
}

interface NewsApiService{
    @GET("latest")
    fun getNews(
        @Query("apikey") apikey : String,
        @Query("q") q :String
    ):Deferred<NewsApiResponse>
}

interface NewsDataWithIdApiService{
    @GET("latest")
    fun getNews(
        @Query("apikey") apikey : String,
        @Query("id") id :String
    ):Deferred<NewsApiResponse>
}

interface NewsNextPageApiService{
    @GET("latest")
    fun getNews(
        @Query("apikey") apikey : String,
        @Query("q") q :String,
        @Query("page") page:String
    ):Deferred<NewsApiResponse>
}

object NextPageApi{
    val NewsNextPageretrofitService:NewsNextPageApiService by lazy {
        newsretrofit.create(NewsNextPageApiService::class.java)
    }
}

object NewsApi {
    val NewsretrofitService: NewsApiService by lazy {
        newsretrofit.create(NewsApiService::class.java)
    }
}

object NewsApiById {
    val NewsWithIdretrofitService: NewsDataWithIdApiService by lazy {
        newsretrofit.create(NewsDataWithIdApiService::class.java)
    }
}

object WeatherApi {
    val weatherretrofitService: WeatherApiService by lazy {
        weatherretrofit.create(WeatherApiService::class.java)
    }
}
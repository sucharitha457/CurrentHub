package com.example.currenthub

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.currenthub.network.WeatherApiResponse


@BindingAdapter("weatherIcon")
fun ImageView.weatherIcon(data: WeatherApiResponse?) {
    data?.let {
        if (it.weather.isNotEmpty()) {
            val iconCode = it.weather[0].icon
            val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            Log.d("WeatherIcon", "Loading icon from URL: $url")

            Glide.with(this.context)
                .load(url)
                .placeholder(android.R.drawable.ic_menu_upload)
                .error(android.R.drawable.stat_notify_error)
                .into(this)
        } else {
            Log.e("WeatherIcon", "Weather data is empty")
            setImageResource(android.R.drawable.stat_notify_error)
        }
    } ?: run {
        Log.e("WeatherIcon", "WeatherApiResponse is null")
        setImageResource(android.R.drawable.stat_notify_error)
    }
}

@BindingAdapter("weathertext")
fun TextView.weatherText(data: WeatherApiResponse?){
    data?.let {
        val weatherCodeToDescription = mapOf(
            // Thunderstorm codes
            200 to "Thunderstorm with light rain",
            201 to "Thunderstorm with rain",
            202 to "Thunderstorm with heavy rain",
            210 to "Light thunderstorm",
            211 to "Thunderstorm",
            212 to "Heavy thunderstorm",
            221 to "Ragged thunderstorm",
            230 to "Thunderstorm with light drizzle",
            231 to "Thunderstorm with drizzle",
            232 to "Thunderstorm with heavy drizzle",

            // Drizzle codes
            300 to "Light drizzle",
            301 to "Drizzle",
            302 to "Heavy drizzle",
            310 to "Light drizzle rain",
            311 to "Drizzle rain",
            312 to "Heavy drizzle rain",
            313 to "Shower rain and drizzle",
            314 to "Heavy shower rain and drizzle",
            321 to "Shower drizzle",

            // Rain codes
            500 to "Light rain",
            501 to "Moderate rain",
            502 to "Heavy rain",
            503 to "Very heavy rain",
            504 to "Extreme rain",
            511 to "Freezing rain",
            520 to "Light shower rain",
            521 to "Shower rain",
            522 to "Heavy shower rain",
            531 to "Ragged shower rain",

            // Snow codes
            600 to "Light snow",
            601 to "Snow",
            602 to "Heavy snow",
            611 to "Sleet",
            612 to "Light sleet shower",
            613 to "Sleet shower",
            615 to "Light rain and snow",
            616 to "Rain and snow",
            620 to "Light shower snow",
            621 to "Shower snow",
            622 to "Heavy shower snow",

            // Atmosphere codes
            701 to "Mist",
            711 to "Smoke",
            721 to "Haze",
            731 to "Dust",
            741 to "Fog",
            751 to "Sand",
            761 to "Dust",
            762 to "Volcanic ash",
            771 to "Squalls",
            781 to "Tornado",

            // Clear and Clouds codes
            800 to "Clear sky",
            801 to "Few clouds",
            802 to "Scattered clouds",
            803 to "Broken clouds",
            804 to "Overcast clouds"
        )

        text = weatherCodeToDescription[it.cod]
    }
}
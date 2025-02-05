package com.example.currenthub.network

import com.squareup.moshi.Json

data class WeatherApiResponse(
    @Json(name = "coord") val coord: Coord,
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "base") val base: String,
    @Json(name = "main") val main: Main,
    @Json(name = "visibility") val visibility: Int,
    @Json(name = "wind") val wind: Wind,
    @Json(name = "clouds") val clouds: Clouds,
    @Json(name = "dt") val dt: Long,
    @Json(name = "sys") val sys: Sys,
    @Json(name = "timezone") val timezone: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "cod") val cod: Int
)

data class Coord(
    @Json(name = "lon") val lon: Double,
    @Json(name = "lat") val lat: Double
)

data class Weather(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

data class Main(
    @Json(name = "temp") val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "sea_level") val seaLevel: Int?,
    @Json(name = "grnd_level") val grndLevel: Int?
)

data class Wind(
    @Json(name = "speed") val speed: Double,
    @Json(name = "deg") val deg: Int,
    @Json(name = "gust") val gust: Double?
)

data class Clouds(
    @Json(name = "all") val all: Int
)

data class Sys(
    @Json(name = "country") val country: String,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long
)

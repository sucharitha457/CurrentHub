package com.example.currenthub.network

import com.squareup.moshi.Json

data class NewsApiResponse(
    @Json(name = "status") val status: String = "",
    @Json(name = "totalResults") val totalResults: Int = 0,
    @Json(name = "results") val results: List<Article> = emptyList(),
    @Json(name = "nextPage") val nextPage: String? = null
)

data class Article(
    @Json(name = "article_id") val articleId: String = "",
    @Json(name = "title") val title: String = "",
    @Json(name = "link") val link: String = "",
    @Json(name = "keywords") val keywords: List<String>? = emptyList(),
    @Json(name = "creator") val creator: List<String>? = emptyList(),
    @Json(name = "video_url") val videoUrl: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "content") val content: String? = null,
    @Json(name = "pubDate") val pubDate: String = "",
    @Json(name = "pubDateTZ") val pubDateTZ: String? = null,
    @Json(name = "image_url") val imageUrl: String? = null,
    @Json(name = "source_id") val sourceId: String? = null,
    @Json(name = "source_priority") val sourcePriority: Int? = 0,
    @Json(name = "source_name") val sourceName: String? = null,
    @Json(name = "source_url") val sourceUrl: String? = null,
    @Json(name = "source_icon") val sourceIcon: String? = null,
    @Json(name = "language") val language: String? = null,
    @Json(name = "country") val country: List<String>? = emptyList(),
    @Json(name = "category") val category: List<String>? = emptyList(),
    @Json(name = "ai_tag") val aiTag: String? = null,
    @Json(name = "sentiment") val sentiment: String? = null,
    @Json(name = "sentiment_stats") val sentimentStats: String? = null,
    @Json(name = "ai_region") val aiRegion: String? = null,
    @Json(name = "ai_org") val aiOrg: String? = null,
    @Json(name = "duplicate") val duplicate: Boolean = false
)

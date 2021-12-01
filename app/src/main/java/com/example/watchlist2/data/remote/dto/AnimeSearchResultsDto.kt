package com.example.watchlist2.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimeSearchResultsDto(
    @Json(name = "request_hash")
    val requestHash: String?,
    @Json(name = "request_cached")
    val requestCached: Boolean,
    @Json(name = "request_cache_expiry")
    val requestCacheExpiry: Int?,
    @Json(name = "results")
    val results: List<AnimeSearchResultDto>,
    @Json(name = "last_page")
    val lastPage: Int?
) {
    @JsonClass(generateAdapter = true)
    data class AnimeSearchResultDto(
        @Json(name = "mal_id")
        val malId: Int,
        @Json(name = "url")
        val url: String?,
        @Json(name = "image_url")
        val imageUrl: String?,
        @Json(name = "title")
        val title: String,
        @Json(name = "airing")
        val airing: Boolean,
        @Json(name = "synopsis")
        val synopsis: String?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "episodes")
        val episodes: Int?,
        @Json(name = "score")
        val score: Double?,
        @Json(name = "start_date")
        val startDate: String?,
        @Json(name = "end_date")
        val endDate: String?,
        @Json(name = "members")
        val members: Int?,
        @Json(name = "rated")
        val rated: String?
    )
}
package com.example.watchlist2.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimeDetailsDto(
    @Json(name = "request_hash")
    val requestHash: String?,
    @Json(name = "request_cached")
    val requestCached: Boolean,
    @Json(name = "request_cache_expiry")
    val requestCacheExpiry: Int?,
    @Json(name = "mal_id")
    val malId: Long,
    @Json(name = "url")
    val url: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "trailer_url")
    val trailerUrl: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "title_english")
    val titleEnglish: String?,
    @Json(name = "title_japanese")
    val titleJapanese: String?,
    @Json(name = "title_synonyms")
    val titleSynonyms: List<String?>,
    @Json(name = "type")
    val type: String?,
    @Json(name = "source")
    val source: String?,
    @Json(name = "episodes")
    val episodes: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "airing")
    val airing: Boolean,
    @Json(name = "duration")
    val duration: String?,
    @Json(name = "rating")
    val rating: String?,
    @Json(name = "score")
    val score: Double?,
    @Json(name = "scored_by")
    val scoredBy: Int?,
    @Json(name = "rank")
    val rank: Int?,
    @Json(name = "popularity")
    val popularity: Int?,
    @Json(name = "favorites")
    val favorites: Int?,
    @Json(name = "synopsis")
    val synopsis: String?,
    @Json(name = "background")
    val background: String?,
    @Json(name = "genres")
    val genres: List<Genre>,
) {

    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "mal_id")
        val malId: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?
    )
}
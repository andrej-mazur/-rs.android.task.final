package com.example.watchlist2.domain.model


data class AnimeSearchResult(
    val id: Int,
    val imageUrl: String?,
    val title: String,
    val type: String?,
    val episodes: Int?,
    val score: Double?,
)
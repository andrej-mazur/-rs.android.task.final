package com.example.watchlist2.domain.model


data class AnimeSearchResult(
    val id: Long,
    val title: String,
    val imageUrl: String? = null,
)
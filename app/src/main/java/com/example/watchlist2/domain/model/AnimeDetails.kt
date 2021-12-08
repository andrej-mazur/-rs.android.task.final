package com.example.watchlist2.domain.model


data class AnimeDetails(
    val id: Long,
    val title: String,
    val imageUrl: String? = null,
    val type: String? = null,
    val episodes: Int? = null,
    val score: Double? = null,
    val rank: Int? = null,
    val synopsis: String? = null,
    val background: String? = null,
    val genres: List<String>? = null,
)
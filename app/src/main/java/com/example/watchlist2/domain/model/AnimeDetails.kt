package com.example.watchlist2.domain.model


data class AnimeDetails(
    val id: Long,
    val imageUrl: String?,
    val title: String,
    val type: String?,
    val episodes: Int?,
    val score: Double?,
    val rank: Int?,
    val synopsis: String?,
    val background: String?,
    val genres: List<String>,
)
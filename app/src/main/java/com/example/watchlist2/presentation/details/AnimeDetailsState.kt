package com.example.watchlist2.presentation.details

import com.example.watchlist2.domain.model.AnimeDetails

data class AnimeDetailsState(
    val isLoading: Boolean = false,
    val anime: AnimeDetails? = null,
    val error: String = ""
)
package com.example.watchlist2.presentation.state

import com.example.watchlist2.domain.model.AnimeDetails

data class AnimeDetailsState(
    val isLoading: Boolean = false,
    val anime: AnimeDetails? = null,
    val error: String = ""
)
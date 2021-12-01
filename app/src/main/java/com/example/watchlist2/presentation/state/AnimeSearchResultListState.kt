package com.example.watchlist2.presentation.state

import com.example.watchlist2.domain.model.AnimeSearchResult

data class AnimeSearchResultListState(
    val isLoading: Boolean = false,
    val coins: List<AnimeSearchResult> = emptyList(),
    val error: String = ""
)
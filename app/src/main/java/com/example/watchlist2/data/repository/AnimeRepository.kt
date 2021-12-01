package com.example.watchlist2.data.repository

import com.example.watchlist2.data.remote.dto.AnimeDetailsDto
import com.example.watchlist2.data.remote.dto.AnimeSearchResultsDto

interface AnimeRepository {

    suspend fun searchAnime(query: String): AnimeSearchResultsDto

    suspend fun getAnimeDetails(malId: String): AnimeDetailsDto
}
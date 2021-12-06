package com.example.watchlist2.data.repository

import com.example.watchlist2.data.remote.JikanApi
import com.example.watchlist2.data.remote.dto.AnimeDetailsDto
import com.example.watchlist2.data.remote.dto.AnimeSearchResultsDto
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val api: JikanApi
) : AnimeRepository {

    override suspend fun searchAnime(query: String): AnimeSearchResultsDto {
        return api.searchAnime(query)
    }

    override suspend fun getAnimeDetails(malId: Long): AnimeDetailsDto {
        return api.getAnimeDetails(malId)
    }
}
package com.example.watchlist2.data.remote

import com.example.watchlist2.data.remote.dto.AnimeDetailsDto
import com.example.watchlist2.data.remote.dto.AnimeSearchResultsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {

    @GET("/v3/search/anime")
    suspend fun searchAnime(
        @Query("q") query: String
    ): AnimeSearchResultsDto

    @GET("/v3/anime/{malId}")
    suspend fun getAnimeDetails(
        @Path("malId") malId: Long
    ): AnimeDetailsDto
}

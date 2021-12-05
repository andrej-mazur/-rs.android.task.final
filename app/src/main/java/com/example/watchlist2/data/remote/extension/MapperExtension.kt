package com.example.watchlist2.data.remote.extension

import com.example.watchlist2.data.remote.dto.AnimeDetailsDto
import com.example.watchlist2.data.remote.dto.AnimeSearchResultsDto
import com.example.watchlist2.domain.model.AnimeDetails
import com.example.watchlist2.domain.model.AnimeSearchResult

fun AnimeDetailsDto.toAnimeDetails(): AnimeDetails {
    return AnimeDetails(
        id = malId,
        imageUrl = imageUrl,
        title = title,
        type = type,
        episodes = episodes,
        score = score,
        rank = rank,
        synopsis = synopsis,
        background = background,
        genres = genres.filter { !it.name.isNullOrBlank() }.map { it.name!! }
    )
}

fun AnimeSearchResultsDto.AnimeSearchResultDto.toAnimeSearchResult(): AnimeSearchResult {
    return AnimeSearchResult(
        id = malId,
        imageUrl = imageUrl,
        title = title,
    )
}

fun AnimeSearchResultsDto.toAnimeSearchResultList(): List<AnimeSearchResult> {
    return results.map { it.toAnimeSearchResult() }
}
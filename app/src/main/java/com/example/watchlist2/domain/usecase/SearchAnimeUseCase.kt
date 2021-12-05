package com.example.watchlist2.domain.usecase

import com.example.watchlist2.data.remote.extension.toAnimeSearchResultList
import com.example.watchlist2.data.repository.AnimeRepository
import com.example.watchlist2.domain.model.AnimeSearchResult
import com.plcoding.cryptocurrencyappyt.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<AnimeSearchResult>>> = flow {
        try {
            emit(Resource.Loading())
            val animeSearchResultList = if (query.length > MIN_QUERY_LENGTH) {
                repository.searchAnime(query).toAnimeSearchResultList()
            } else {
                emptyList()
            }
            emit(Resource.Success(animeSearchResultList))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error."))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        const val MIN_QUERY_LENGTH = 3
    }
}
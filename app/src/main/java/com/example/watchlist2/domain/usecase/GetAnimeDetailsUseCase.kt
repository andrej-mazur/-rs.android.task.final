package com.example.watchlist2.domain.usecase

import com.example.watchlist2.common.Resource
import com.example.watchlist2.data.remote.extension.toAnimeDetails
import com.example.watchlist2.data.repository.AnimeRepository
import com.example.watchlist2.domain.model.AnimeDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnimeDetailsUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(id: Long): Flow<Resource<AnimeDetails>> = flow {
        try {
            emit(Resource.Loading())
            val animeDetails = repository.getAnimeDetails(id).toAnimeDetails()
            emit(Resource.Success(animeDetails))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error."))
        }
    }
}
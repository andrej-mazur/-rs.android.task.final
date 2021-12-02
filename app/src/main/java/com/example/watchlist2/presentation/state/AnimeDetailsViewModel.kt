package com.example.watchlist2.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist2.domain.usecase.GetAnimeDetailsUseCase
import com.plcoding.cryptocurrencyappyt.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimeDetailsState())
    val uiState: StateFlow<AnimeDetailsState> = _uiState

    init {
        getAnimeDetails("820")
    }

    private fun getAnimeDetails(id: String) {
        getAnimeDetailsUseCase(id)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = AnimeDetailsState(anime = result.data)
                    }
                    is Resource.Error -> {
                        _uiState.value = AnimeDetailsState(error = result.message ?: "An unexpected error occurred")
                    }
                    is Resource.Loading -> {
                        _uiState.value = AnimeDetailsState(isLoading = true)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
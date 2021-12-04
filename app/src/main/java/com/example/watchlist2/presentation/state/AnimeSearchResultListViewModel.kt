package com.example.watchlist2.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist2.domain.model.AnimeSearchResult
import com.example.watchlist2.domain.usecase.SearchAnimeUseCase
import com.plcoding.cryptocurrencyappyt.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AnimeSearchResultListViewModel @Inject constructor(
    private val searchAnimeUseCase: SearchAnimeUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<Resource<List<AnimeSearchResult>>> = MutableStateFlow(Resource.Loading())
    val uiState: StateFlow<Resource<List<AnimeSearchResult>>> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        searchAnime("eureka")
    }

    private fun searchAnime(query: String) {
        searchJob?.cancel()
        searchJob = searchAnimeUseCase(query)
            .onEach { result -> _uiState.value = result }
            .launchIn(viewModelScope)
    }
}
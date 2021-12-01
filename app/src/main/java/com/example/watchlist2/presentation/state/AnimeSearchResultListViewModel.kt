package com.example.watchlist2.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist2.domain.model.AnimeSearchResult
import com.example.watchlist2.domain.usecase.SearchAnimeUseCase
import com.plcoding.cryptocurrencyappyt.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeSearchResultListViewModel @Inject constructor(
    private val searchAnimeUseCase: SearchAnimeUseCase
) : ViewModel() {

    private val _uiState:MutableStateFlow<Resource<List<AnimeSearchResult>>> = MutableStateFlow(Resource.Loading())
    val uiState: StateFlow<Resource<List<AnimeSearchResult>>> = _uiState

    init {
        searchAnime("eureka")
    }

    private fun searchAnime(query: String) {
        searchAnimeUseCase(query).onEach { result ->
            _uiState.value = result
        }.launchIn(viewModelScope)
    }
}
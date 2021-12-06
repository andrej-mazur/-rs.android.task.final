package com.example.watchlist2.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist2.common.Resource
import com.example.watchlist2.domain.model.AnimeSearchResult
import com.example.watchlist2.domain.usecase.SearchAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(
    private val searchAnimeUseCase: SearchAnimeUseCase
) : ViewModel() {

    private val _currentQuery = MutableStateFlow<String?>(null)
    private val currentQuery: StateFlow<String?> = _currentQuery.asStateFlow()

    val uiState: StateFlow<Resource<List<AnimeSearchResult>>> = currentQuery
        .filterNotNull()
        .debounce(1000)
        .flatMapLatest {
            searchAnimeUseCase(it)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, Resource.Empty())

    fun setCurrentQuery(query: String) {
        _currentQuery.value = query
    }
}
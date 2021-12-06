package com.example.watchlist2.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist2.common.Resource
import com.example.watchlist2.domain.model.AnimeDetails
import com.example.watchlist2.domain.usecase.GetAnimeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase
) : ViewModel() {

    private val _currentId = MutableStateFlow<Long?>(null)
    private val currentId: StateFlow<Long?> = _currentId.asStateFlow()

    val uiState: StateFlow<Resource<AnimeDetails>> = currentId
        .filterNotNull()
        .flatMapLatest {
            getAnimeDetailsUseCase(it)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, Resource.Empty())

    fun setCurrentId(id: Long) {
        _currentId.value = id
    }
}
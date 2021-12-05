package com.example.watchlist2.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist2.domain.model.AnimeDetails
import com.example.watchlist2.domain.usecase.GetAnimeDetailsUseCase
import com.plcoding.cryptocurrencyappyt.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<AnimeDetails>>(Resource.Loading())
    val uiState: StateFlow<Resource<AnimeDetails>> = _uiState

    private var job: Job? = null

    fun getAnimeDetails(id: String) {
        job?.cancel()
        job = getAnimeDetailsUseCase(id)
            .onEach { result -> _uiState.value = result }
            .launchIn(viewModelScope)
    }
}
package com.example.task5.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.task5.api.data.Cat
import com.example.task5.di.locateLazy
import com.example.task5.datasource.CatPagingRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel() : ViewModel() {

    private val repository: CatPagingRepository by locateLazy()

    private var currentSearchResult: Flow<PagingData<Cat>>? = null

    fun searchImages(): Flow<PagingData<Cat>> {
        val newResult = repository.searchStream().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

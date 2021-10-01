package com.example.task5.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.task5.api.data.Cat
import com.example.task5.datasource.CatPagingRepository
import com.example.task5.di.locateLazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatViewModel : ViewModel() {

    private val repository: CatPagingRepository by locateLazy()

    private val _catPageFlow = repository.getCatPageFlow()

    val catPageFlow: Flow<PagingData<Cat>> = _catPageFlow.cachedIn(viewModelScope)

    private val _catFlow = MutableStateFlow<Cat?>(null)

    val catFlow: StateFlow<Cat?> = _catFlow

    fun updateCatFlow(cat: Cat) {
        _catFlow.tryEmit(cat)
    }
}

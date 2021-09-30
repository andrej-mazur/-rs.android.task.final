package com.example.task5.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.task5.api.data.Cat
import com.example.task5.constant.Pagination
import kotlinx.coroutines.flow.Flow

class CatPagingRepository(
    private val service: CatService
) {

    fun searchStream(): Flow<PagingData<Cat>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Pagination.DEFAULT_PAGE_SIZE),
            pagingSourceFactory = { CatPagingSource(service) }
        ).flow
    }
}

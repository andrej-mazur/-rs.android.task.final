package com.example.task5.datasource

import com.example.task5.api.CatApi
import com.example.task5.constant.Headers
import com.example.task5.constant.Pagination
import com.example.task5.data.CatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatService(
    private val api: CatApi
) {

    suspend fun search(
        pageNumber: Int = Pagination.DEFAULT_PAGE_NUMBER,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE,
        sortOrder: String = Pagination.DEFAULT_SORT_ORDER
    ): CatResponse {
        return withContext(Dispatchers.IO) {
            val response = api.search(pageNumber = pageNumber, pageSize = pageSize, sortOrder = sortOrder)
            if (response.isSuccessful) {
                val cats = response.body() ?: emptyList()

                val headers = response.headers()
                val responseCount = headers[Headers.PAGINATION_COUNT]?.toIntOrNull() ?: 0
                val responseLimit = headers[Headers.PAGINATION_LIMIT]?.toIntOrNull() ?: 0
                val responsePage = headers[Headers.PAGINATION_PAGE]?.toIntOrNull() ?: 0

                CatResponse(cats, responseCount, responseLimit, responsePage)
            } else {
                CatResponse(emptyList(), 0, pageSize, pageNumber)
            }
        }
    }
}

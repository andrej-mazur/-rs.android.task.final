package com.example.task5.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.task5.api.data.Cat
import com.example.task5.constant.Pagination
import okio.IOException
import retrofit2.HttpException

class CatPagingSource(
    private val service: CatService
) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        val pageNumber = params.key ?: Pagination.DEFAULT_PAGE_NUMBER
        val pageSize = params.loadSize
        return try {
            val response = service.search(pageNumber, pageSize)
            LoadResult.Page(
                data = response.cats,
                prevKey = if (pageNumber <= Pagination.DEFAULT_PAGE_NUMBER) null else pageNumber - 1,
                nextKey = if (pageNumber >= response.count / response.limit) null else pageNumber + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

package com.an9ar.jetheroes.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.an9ar.jetheroes.data.HeroRepository
import com.an9ar.jetheroes.data.dto.HeroResponse

class HeroDataSource(
    private val repository: HeroRepository
) : PagingSource<Int, HeroResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HeroResponse> {
        return try {
            val offset = params.key ?: 0
            val movieListResponse = repository.loadHeroes(offset, 20)

            LoadResult.Page(
                data = movieListResponse.pagingInfo.results,
                prevKey = if (offset == 0) null else offset - 20,
                nextKey = movieListResponse.pagingInfo.offset.plus(20)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HeroResponse>): Int? {
        // todo возвращать правильный ключ
        return null
    }
}
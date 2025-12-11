package com.an9ar.jetheroes.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.an9ar.jetheroes.data.repository.HeroRepository
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoDto

class HeroDataSource constructor(
    private val repository: HeroRepository
) : PagingSource<Int, HeroInfoDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HeroInfoDto> {
        return try {
            // Start from ID 1, use key as the starting ID
            val startId = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20) // Limit page size to avoid too many requests
            
            val heroes = mutableListOf<HeroInfoDto>()
            var currentId = startId
            var attempts = 0
            val maxAttempts = pageSize * 3 // Try up to 3x page size to account for failed requests
            
            // Fetch heroes by ID until we have enough or reach max ID
            while (heroes.size < pageSize && currentId <= HeroRepository.MAX_HERO_ID && attempts < maxAttempts) {
                attempts++
                when (val result = repository.loadHeroById(currentId)) {
                    is GreatResult.Success -> {
                        heroes.add(result.data)
                    }
                    is GreatResult.Error -> {
                        // Skip failed IDs (some IDs might not exist)
                    }
                    is GreatResult.Progress -> {
                        // Skip
                    }
                }
                currentId++
            }
            
            val nextKey = if (currentId > HeroRepository.MAX_HERO_ID || heroes.isEmpty()) {
                null
            } else {
                currentId
            }

            LoadResult.Page(
                data = heroes,
                prevKey = if (startId <= 1) null else (startId - pageSize).coerceAtLeast(1),
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HeroInfoDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        } ?: 1
    }
}
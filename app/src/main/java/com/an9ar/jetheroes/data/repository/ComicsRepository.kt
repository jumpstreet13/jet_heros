package com.an9ar.jetheroes.data.repository

import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsDto
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperDto
import com.an9ar.jetheroes.service.HeroService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepository @Inject constructor(
    private val service: HeroService
) {

    suspend fun loadComicDetailInfo(id: String): GreatResult<ComicsDto> {
        return GreatResult.Success(
            service.getComicsDetailInfo(id).info.results.first()
        )
    }

    suspend fun loadComicsById(id: String): GreatResult<ComicsWrapperDto> {
        return GreatResult.Success(service.getComicsInfoById(id).info)
    }
}
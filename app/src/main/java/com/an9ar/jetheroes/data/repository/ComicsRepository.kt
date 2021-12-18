package com.an9ar.jetheroes.data.repository

import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperDto
import com.an9ar.jetheroes.service.HeroService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepository @Inject constructor(
    private val service: HeroService
) {

    suspend fun loadComics(url: String): GreatResult<ComicsWrapperDto> {
        return GreatResult.Success(
            service.getComicsInfo(url).await().info
        )
    }
}
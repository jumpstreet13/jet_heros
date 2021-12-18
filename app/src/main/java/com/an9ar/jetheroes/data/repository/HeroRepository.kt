package com.an9ar.jetheroes.data.repository

import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoDto
import com.an9ar.jetheroes.data.dto.heroinfo.HeroResponseDto
import com.an9ar.jetheroes.service.HeroService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroRepository @Inject constructor(
    private val heroService: HeroService
) {

    suspend fun loadHeroes(offset: Int, limit: Int): HeroResponseDto {
        return heroService.getHeroesAsync(offset = offset, limit = limit).await()
    }

    suspend fun loadHeroInfoById(heroId: Long): GreatResult<HeroInfoDto> {
        return GreatResult.Success(
            heroService.getHeroInfo(heroId = heroId).await().info.results.first()
        )
    }
}
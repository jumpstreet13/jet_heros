package com.an9ar.jetheroes.data.repository

import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoDto
import com.an9ar.jetheroes.service.HeroService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroRepository @Inject constructor(
    private val heroService: HeroService
) {
    companion object {
        private const val API_KEY = "e0773dabeafe078fa5b74691f403529a"
        // SuperHero API has characters from ID 1 to 731
        const val MAX_HERO_ID = 731
    }

    suspend fun loadHeroById(heroId: Int): GreatResult<HeroInfoDto> {
        return try {
            val response = heroService.getHeroById(API_KEY, heroId)
            if (response.response == "success") {
                GreatResult.Success(response)
            } else {
                GreatResult.Error(Exception("Hero not found: ${response.response}"))
            }
        } catch (e: Exception) {
            GreatResult.Error(e)
        }
    }

    suspend fun loadHeroInfoById(heroId: Long): GreatResult<HeroInfoDto> {
        return loadHeroById(heroId.toInt())
    }
}
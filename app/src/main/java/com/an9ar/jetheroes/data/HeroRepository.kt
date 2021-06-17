package com.an9ar.jetheroes.data

import com.an9ar.jetheroes.data.dto.HeroResponseDto
import com.an9ar.jetheroes.service.HeroService
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroRepository @Inject constructor(
    private val heroService: HeroService
) {

    suspend fun loadHeroes(offset: Int, limit: Int): HeroResponseDto {
        return heroService.getHeroesAsync(offset = offset, limit = limit).await()
    }
}
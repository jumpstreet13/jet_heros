package com.an9ar.jetheroes.data

import com.an9ar.jetheroes.data.dto.HeroResponse
import com.an9ar.jetheroes.service.HeroService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class HeroRepository @Inject constructor(
    private val heroService: HeroService
) {

    suspend fun loadHeroes(): HeroResponse {
        return heroService.getHeroesAsync().await().results.first()
    }
}
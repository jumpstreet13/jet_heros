package com.an9ar.jetheroes.service

import com.an9ar.jetheroes.data.dto.HeroResponseDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroService {

    @GET("{access-token}/search/{name}")
    fun getHeroesAsync(
        @Path("access-token") accessToken: String = "1243325372714127",
        @Path("name") characterId: String = "Batman"
    ): Deferred<HeroResponseDto>
}

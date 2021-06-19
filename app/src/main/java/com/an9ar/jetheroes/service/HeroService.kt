package com.an9ar.jetheroes.service

import com.an9ar.jetheroes.data.dto.HeroInfoResponse
import com.an9ar.jetheroes.data.dto.HeroResponseDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeroService {

    @GET("v1/public/characters")
    fun getHeroesAsync(
        @Query("ts") ts: Int = 1,
        @Query("apikey") accessToken: String = "7ed0f05931060d1bb3810f9f51018b90",
        @Query("hash") hash: String = "b04215dfce5be688fcf58b1d57fad338",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Deferred<HeroResponseDto>

    @GET("v1/public/characters/{characterId}")
    fun getHeroInfo(
        @Path("characterId") heroId: Long,
        @Query("ts") ts: Int = 1,
        @Query("apikey") accessToken: String = "7ed0f05931060d1bb3810f9f51018b90",
        @Query("hash") hash: String = "b04215dfce5be688fcf58b1d57fad338",
    ): Deferred<HeroInfoResponse>
}

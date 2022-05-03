package com.an9ar.jetheroes.service

import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperDto
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperResponse
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoResponse
import com.an9ar.jetheroes.data.dto.heroinfo.HeroResponseDto
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface HeroService {

    @GET("v1/public/characters")
    fun getHeroesAsync(
        @Query("ts") ts: Int = 1,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Deferred<HeroResponseDto>

    @GET("v1/public/characters/{characterId}")
    fun getHeroInfo(
        @Path("characterId") heroId: Long,
        @Query("ts") ts: Int = 1,
    ): Deferred<HeroInfoResponse>

    @GET
    fun getComicsInfo(
        @Url url: String,
        @Query("ts") ts: Int = 1
    ): Deferred<ComicsWrapperResponse>

    @GET("v1/public/characters/{comicsId}/comics")
    suspend fun getComicsInfoById(
        @Path("comicsId") comicsId: String,
        @Query("ts") ts: Int = 1
    ): ComicsWrapperResponse
}

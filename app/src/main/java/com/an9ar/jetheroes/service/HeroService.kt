package com.an9ar.jetheroes.service

import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperResponse
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoResponse
import com.an9ar.jetheroes.data.dto.heroinfo.HeroResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface HeroService {
    @GET("v1/public/characters")
    suspend fun getHeroesAsync(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): HeroResponseDto

    @GET("v1/public/characters/{characterId}")
    suspend fun getHeroInfo(@Path("characterId") heroId: Long): HeroInfoResponse

    @GET
    suspend fun getComicsInfo(@Url url: String): ComicsWrapperResponse

    @GET("v1/public/characters/{comicsId}/comics")
    suspend fun getComicsInfoById(@Path("comicsId") comicsId: String): ComicsWrapperResponse

    @GET("/v1/public/comics/{comicId}")
    suspend fun getComicsDetailInfo(@Path("comicId") comicsId: String): ComicsWrapperResponse
}

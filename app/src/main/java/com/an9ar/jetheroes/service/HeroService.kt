package com.an9ar.jetheroes.service

import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperResponse
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeroService {
    // SuperHero API: Get character by ID
    // Format: https://superheroapi.com/api/{access-token}/{character-id}
    @GET("{apiKey}/{characterId}")
    suspend fun getHeroById(
        @Path("apiKey") apiKey: String,
        @Path("characterId") characterId: Int
    ): HeroInfoResponse
    
    // Marvel API: Get comics for a character
    // Using Marvel API for comics data
    @GET("https://gateway.marvel.com/v1/public/characters/{characterId}/comics")
    suspend fun getComicsByCharacterId(
        @Path("characterId") characterId: String,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): ComicsWrapperResponse
    
    // Marvel API: Get comic detail
    @GET("https://gateway.marvel.com/v1/public/comics/{comicId}")
    suspend fun getComicDetail(
        @Path("comicId") comicId: String,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): ComicsWrapperResponse
}

package com.an9ar.jetheroes.data.repository

import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsDto
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperDto
import com.an9ar.jetheroes.service.HeroService
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepository @Inject constructor(
    private val heroService: HeroService
) {
    companion object {
        // Marvel API credentials for comics
        private const val MARVEL_PUBLIC_KEY = "7ed0f05931060d1bb3810f9f51018b90"
        private const val MARVEL_PRIVATE_KEY = "YOUR_PRIVATE_KEY" // Replace with actual private key if available
        private const val MARVEL_HASH = "b04215dfce5be688fcf58b1d57fad338" // Pre-calculated hash for ts=1
        
        private fun generateMarvelAuth(): Pair<String, String> {
            // Using static timestamp and hash for now
            // For production, calculate: MD5(ts + privateKey + publicKey)
            val ts = "1"
            return ts to MARVEL_HASH
        }
    }

    suspend fun loadComicDetailInfo(id: String): GreatResult<ComicsDto> {
        return try {
            val (ts, hash) = generateMarvelAuth()
            val response = heroService.getComicDetail(
                comicId = id,
                apikey = MARVEL_PUBLIC_KEY,
                ts = ts,
                hash = hash
            )
            if (response.info.results.isNotEmpty()) {
                GreatResult.Success(response.info.results.first())
            } else {
                GreatResult.Error(Exception("Comic not found"))
            }
        } catch (e: Exception) {
            GreatResult.Error(e)
        }
    }

    suspend fun loadComicsById(id: String): GreatResult<ComicsWrapperDto> {
        return try {
            val (ts, hash) = generateMarvelAuth()
            val response = heroService.getComicsByCharacterId(
                characterId = id,
                apikey = MARVEL_PUBLIC_KEY,
                ts = ts,
                hash = hash,
                limit = 50,
                offset = 0
            )
            GreatResult.Success(response.info)
        } catch (e: Exception) {
            GreatResult.Error(e)
        }
    }
}
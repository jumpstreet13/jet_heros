package com.an9ar.jetheroes.di

import com.an9ar.jetheroes.interceptors.HeaderInterceptor
import com.an9ar.jetheroes.service.HeroService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.IllegalArgumentException

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @ExperimentalSerializationApi
    @Provides
    fun provideHeroService(okHttpClient: OkHttpClient): HeroService {
        val contentType = MediaType.parse("application/json")
            ?: throw IllegalArgumentException("Should be not null")
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
            .create(HeroService::class.java)
    }

    @Provides
    fun provideOtherInterceptorOkHttpClient(
        otherInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(otherInterceptor)
            .build()
    }
}
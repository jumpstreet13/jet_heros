package com.an9ar.jetheroes.di

import com.an9ar.jetheroes.interceptors.HeaderInterceptor
import com.an9ar.jetheroes.service.HeroService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @ExperimentalSerializationApi
    @Provides
    fun provideHeroService(okHttpClient: OkHttpClient): HeroService {
        val contentType = "application/json".toMediaTypeOrNull()
            ?: throw IllegalArgumentException("Should be not null")
        return Retrofit.Builder()
            .baseUrl("https://www.superheroapi.com/api.php/")
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(HeroService::class.java)
    }

    @Provides
    fun provideOtherInterceptorOkHttpClient(
        otherInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(otherInterceptor)
            .build()
    }
}
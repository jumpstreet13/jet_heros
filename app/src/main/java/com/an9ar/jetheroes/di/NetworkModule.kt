package com.an9ar.jetheroes.di

import com.an9ar.jetheroes.interceptors.HeaderInterceptor
import com.an9ar.jetheroes.service.HeroService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @Provides
    fun provideHeroService(okHttpClient: OkHttpClient): HeroService {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com")
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
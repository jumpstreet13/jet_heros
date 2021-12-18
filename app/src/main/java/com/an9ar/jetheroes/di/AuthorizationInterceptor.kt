package com.an9ar.jetheroes.di

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val AUTHORIZATION_HEADER = "apikey"
private const val HASH = "hash"

open class AuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return proceedRequestWithAuthorization(chain)
    }

    private fun proceedRequestWithAuthorization(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
                .addQueryParameter(
                        AUTHORIZATION_HEADER,
                        "7ed0f05931060d1bb3810f9f51018b90")
                .addQueryParameter(
                        HASH,
                        "b04215dfce5be688fcf58b1d57fad338"
                )
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
package com.an9ar.jetheroes.di

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val TIMESTAMP = "ts"

open class TimeStampInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return proceedRequestWithTimestamp(chain)
    }

    private fun proceedRequestWithTimestamp(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(TIMESTAMP, "1")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
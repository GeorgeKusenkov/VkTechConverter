package com.egasmith.vktechconverter.data.api.utils

import okhttp3.Interceptor
import okhttp3.Response

class ExchangeRateApiKeyInterceptor(private val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("X-API-KEY", apiKey)
                .build()
        )
    }
}
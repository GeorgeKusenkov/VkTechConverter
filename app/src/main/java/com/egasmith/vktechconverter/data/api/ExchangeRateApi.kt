package com.egasmith.vktechconverter.data.api

import com.egasmith.vktechconverter.BuildConfig
import com.egasmith.vktechconverter.data.api.utils.AssetJsonReader
import com.egasmith.vktechconverter.data.api.utils.ExchangeRateApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ExchangeRateApi {
}

class ExchangeRateApiProvider (jsonReader: AssetJsonReader) {
    private val exchangeRateApiKeyInterceptor = ExchangeRateApiKeyInterceptor(BuildConfig.API_KEY)

    fun provideExchangeRateApiApi(): ExchangeRateApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(exchangeRateApiKeyInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ExchangeRateApi::class.java)
    }
}
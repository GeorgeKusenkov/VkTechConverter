package com.egasmith.vktechconverter.data.api

import com.egasmith.vktechconverter.data.api.model.ExchangeRateResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val apiKey = "e2b966bb23a9ff6c9a1751fa"
const val BASE_URL = "https://v6.exchangerate-api.com/"

interface ExchangeRateApi {
    @GET("v6/{apiKey}/latest/{baseCurrency}")
    suspend fun getLatestRates(
        @Path("apiKey") apiKey: String,
        @Path("baseCurrency") baseCurrency: String
    ): Response<ExchangeRateResponse>
}

object RetrofitProvider {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val api: ExchangeRateApi = RetrofitProvider.retrofit.create(ExchangeRateApi::class.java)

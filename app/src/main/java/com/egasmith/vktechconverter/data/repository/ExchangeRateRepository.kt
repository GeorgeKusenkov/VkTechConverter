package com.egasmith.vktechconverter.data.repository

import com.egasmith.vktechconverter.data.api.api
import com.egasmith.vktechconverter.data.api.apiKey
import okhttp3.Response

class ExchangeRateRepository {
    suspend fun getLatestRates(baseCurrency: String) =
        api.getLatestRates(apiKey, baseCurrency)
}
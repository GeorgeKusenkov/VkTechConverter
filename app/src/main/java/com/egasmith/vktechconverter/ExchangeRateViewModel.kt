package com.egasmith.vktechconverter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egasmith.vktechconverter.data.repository.Currencies
import com.egasmith.vktechconverter.data.api.model.Currency
import com.egasmith.vktechconverter.data.api.model.ExchangeRateResponse
import com.egasmith.vktechconverter.data.repository.ExchangeRateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExchangeRateViewModel(
    private val repository: ExchangeRateRepository
) : ViewModel() {
    private val _exchangeRates = MutableStateFlow<ExchangeRateResponse?>(null)

    private val _fromCurrency = MutableStateFlow(Currencies.list.first())
    val fromCurrency: StateFlow<Currency> = _fromCurrency

    private val _toCurrency = MutableStateFlow(Currencies.list[1])
    val toCurrency: StateFlow<Currency> = _toCurrency

    private val _amount = MutableStateFlow("1")
    val amount: StateFlow<String> = _amount

    private val _convertedAmount = MutableStateFlow<Double?>(null)
    val convertedAmount: StateFlow<Double?> = _convertedAmount

    fun selectFromCurrency(currency: Currency) {
        _fromCurrency.value = currency
        convert()
    }

    fun selectToCurrency(currency: Currency) {
        _toCurrency.value = currency
        convert()
    }

    fun setAmount(newAmount: String) {
        _amount.value = newAmount
        convert()
    }

    init {
        fetchExchangeRates()
    }

    private fun convert() {
        viewModelScope.launch {
            val amount = _amount.value.toDoubleOrNull() ?: return@launch
            val fromRate = _exchangeRates.value?.conversionRates?.get(_fromCurrency.value.code) ?: return@launch
            val toRate = _exchangeRates.value?.conversionRates?.get(_toCurrency.value.code) ?: return@launch

            val convertedAmount = amount * (toRate / fromRate)
            _convertedAmount.value = convertedAmount
        }
    }

    fun fetchExchangeRates() {
        viewModelScope.launch {
            try {
                // запрашиваем USD как тестовый
                val response = repository.getLatestRates("USD")
                if (response.isSuccessful) {
                    _exchangeRates.value = response.body()
                    convert()
                } else {
                    Log.d("fetchExchangeRates", "Ошибка запроса")
                }
            } catch (e: Exception) {
                Log.d("fetchExchangeRates", e.toString())
            }
        }
    }
}
package com.egasmith.vktechconverter.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.egasmith.vktechconverter.data.api.model.Currency

@Composable
fun ConversionResult(convertedAmount: Double?, toCurrency: Currency) {
    if (convertedAmount != null) {
        Text(
            text = "Конвертированная сумма: $convertedAmount ${toCurrency.code}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
package com.egasmith.vktechconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egasmith.vktechconverter.data.api.model.Currency
import com.egasmith.vktechconverter.data.repository.ExchangeRateRepository
import com.egasmith.vktechconverter.ui.components.AmountInputField
import com.egasmith.vktechconverter.ui.components.ConversionResult
import com.egasmith.vktechconverter.ui.components.DropList
import com.egasmith.vktechconverter.ui.theme.VkTechConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkTechConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val repository = ExchangeRateRepository()
                    val viewModel = ExchangeRateViewModel(repository)
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: ExchangeRateViewModel) {
    val fromCurrency by viewModel.fromCurrency.collectAsState()
    val toCurrency by viewModel.toCurrency.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val convertedAmount by viewModel.convertedAmount.collectAsState()

    val onAmountChange: (String) -> Unit = { newAmount ->
        viewModel.setAmount(newAmount)
    }

    val fromCurrencyChange: (Currency) -> Unit = { currency ->
        viewModel.selectFromCurrency(currency)
    }

    val toCurrencyChange: (Currency) -> Unit = { currency ->
        viewModel.selectToCurrency(currency)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.size(60.dp))

        Text(
            text = "Простой конвертер валют",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.size(4.dp))

        AmountInputField(
            amount = amount,
            onAmountChange = onAmountChange,
            modifier = Modifier.fillMaxWidth()
        )

        DropList(
            label = "Из валюты",
            selectedCurrency = fromCurrency,
            onCurrencySelected = fromCurrencyChange,
            modifier = Modifier.fillMaxWidth()
        )

        DropList(
            label = "В валюту",
            selectedCurrency = toCurrency,
            onCurrencySelected = toCurrencyChange,
            modifier = Modifier.fillMaxWidth()
        )

        ConversionResult(convertedAmount, toCurrency)

        Button(
            onClick = viewModel::fetchExchangeRates,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text("Обновить данные")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VkTechConverterTheme {
    }
}
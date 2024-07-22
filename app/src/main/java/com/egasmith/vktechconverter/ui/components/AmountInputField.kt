package com.egasmith.vktechconverter.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AmountInputField(
    amount: String,
    onAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var amountValue by remember { mutableStateOf(amount) }

    OutlinedTextField(
        value = amountValue,
        // Разрешаем только цифры и одну точку
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() || it == '.' }
            val finalValue = if (filteredValue.count { it == '.' } > 1) {
                filteredValue.substring(0, filteredValue.lastIndexOf('.'))
            } else {
                filteredValue
            }
            amountValue = finalValue
            onAmountChange(finalValue)
        },
        label = { Text("Сумма") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier
    )
}
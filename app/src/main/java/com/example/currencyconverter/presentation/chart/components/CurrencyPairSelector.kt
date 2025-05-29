package com.example.currencyconverter.presentation.chart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.presentation.converter.components.CurrencyDropdown

@Composable
fun CurrencyPairSelector(
    currencyFrom: String,
    currencyTo: String,
    currencyList: List<Currency>,
    onCurrencySelected: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CurrencyDropdown(
            selectedCurrency = currencyFrom,
            onCurrencySelected = { onCurrencySelected(it, true) },
            currencyList = currencyList,
            modifier = Modifier.weight(1f)
        )

        CurrencyDropdown(
            selectedCurrency = currencyTo,
            onCurrencySelected = { onCurrencySelected(it, false) },
            currencyList = currencyList,
            modifier = Modifier.weight(1f)
        )
    }
}

package com.example.currencyconverter.presentation.converter

import com.example.currencyconverter.domain.model.Currency

data class ConverterState(
    val amountFrom: String = "",
    val amountTo: String = "",
    val currencyFrom: String = "USD",
    val currencyTo: String = "EUR",
    val currencyList: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
)

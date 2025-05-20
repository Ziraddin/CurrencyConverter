package com.example.currencyconverter.presentation.converter

data class ConverterState(
    val amountFrom: String = "",
    val amountTo: String = "",
    val currencyFrom: String = "USD",
    val currencyTo: String = "EUR",
    val currencyList: List<String> = emptyList(),
    val isLoading: Boolean = false,
)

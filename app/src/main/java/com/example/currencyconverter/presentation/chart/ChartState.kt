package com.example.currencyconverter.presentation.chart

import com.example.currencyconverter.domain.model.Currency

data class ChartState(
    val currencyFrom: String = "USD",
    val currencyTo: String = "EUR",
    val currencyList: List<Currency> = emptyList(),
    val timePeriod: String = "48H",
    val historicalRates: List<Pair<String, Double>> = emptyList(),
    val isLoading: Boolean = false,
    val conversionRate: Double = 1.0,
)
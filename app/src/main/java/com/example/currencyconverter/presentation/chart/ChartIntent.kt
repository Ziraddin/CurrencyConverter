package com.example.currencyconverter.presentation.chart

sealed class ChartIntent {
    data class SelectCurrency(val currency: String, val currencyFrom: Boolean) : ChartIntent()
    data object SwapCurrencies : ChartIntent()
    data object LoadCurrencies : ChartIntent()
    data object GetHistoricalRates : ChartIntent()
    data class SetTimePeriod(val period: String) : ChartIntent()
}
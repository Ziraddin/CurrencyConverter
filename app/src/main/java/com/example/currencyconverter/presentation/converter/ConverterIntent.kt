package com.example.currencyconverter.presentation.converter

sealed class ConverterIntent {
    data class EnterAmount(val amount: String) : ConverterIntent()
    data class SelectCurrency(val currency: String, val currencyFrom: Boolean) : ConverterIntent()
    data object SwapCurrencies : ConverterIntent()
    data object LoadCurrencies : ConverterIntent()
    data object GetStatus : ConverterIntent()
}

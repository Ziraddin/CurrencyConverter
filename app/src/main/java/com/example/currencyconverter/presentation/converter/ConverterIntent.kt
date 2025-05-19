package com.example.currencyconverter.presentation.converter

sealed class ConverterIntent {
    data class EnterAmount(val amount: String, val isFirstField: Boolean) : ConverterIntent()
    data class SelectCurrency(val currency: String, val isFirstField: Boolean) : ConverterIntent()
    data object SwapCurrencies : ConverterIntent()
    data object LoadCurrencies : ConverterIntent()
}

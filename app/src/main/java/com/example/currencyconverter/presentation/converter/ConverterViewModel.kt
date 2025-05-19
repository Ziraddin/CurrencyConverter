package com.example.currencyconverter.presentation.converter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ConverterViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        ConverterState(
            amountFrom = "100",
            amountTo = "92.50",
            currencyFrom = "USD",
            currencyTo = "EUR",
            currencyList = listOf("USD", "EUR", "GBP", "JPY", "UZS")
        )
    )
    val state: StateFlow<ConverterState> = _state

    fun onIntent(intent: ConverterIntent) {
        when (intent) {
            ConverterIntent.SwapCurrencies -> {
                _state.update { current ->
                    current.copy(
                        amountFrom = current.amountTo,
                        amountTo = current.amountFrom,
                        currencyFrom = current.currencyTo,
                        currencyTo = current.currencyFrom,
                    )
                }
            }

            is ConverterIntent.EnterAmount -> {

            }

            is ConverterIntent.LoadCurrencies -> {

            }

            is ConverterIntent.SelectCurrency -> {

            }
        }
    }

}

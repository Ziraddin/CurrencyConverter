package com.example.currencyconverter.presentation.converter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.CurrencyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val useCases: CurrencyUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ConverterState())
    val state: StateFlow<ConverterState> = _state

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    fun onIntent(intent: ConverterIntent) {
        when (intent) {
            ConverterIntent.SwapCurrencies -> {
                swapCurrencies()
            }

            is ConverterIntent.EnterAmount -> {
                _state.update { it.copy(amountFrom = intent.amount) }
                convertCurrency()
            }

            is ConverterIntent.LoadCurrencies -> {
                getCurrencies()
            }

            is ConverterIntent.SelectCurrency -> {
                selectCurrency(intent.currency, intent.currencyFrom)
                convertCurrency()
            }

            is ConverterIntent.GetStatus -> {
                getStatus()
            }

        }
    }

    private fun getStatus() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                val response = useCases.getStatus()
                if (response.isSuccessful) {
                    val status = response.body()
                    Log.d("ConverterViewModel", "getStatus: ${status?.monthlyTotal}")
                } else {
                    _error.emit("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _error.emit("Error: ${e.message}")
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun swapCurrencies() {
        _state.update { current ->
            current.copy(
                amountFrom = current.amountTo,
                amountTo = current.amountFrom,
                currencyFrom = current.currencyTo,
                currencyTo = current.currencyFrom
            )
        }
    }

    private fun convertCurrency() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                val response = useCases.getLatestRates(
                    baseCurrency = state.value.currencyFrom,
                    targetCurrencies = listOf(state.value.currencyTo),
                    amount = state.value.amountFrom.toDouble()
                )

                if (response.isSuccessful) {
                    val rate = response.body()?.get(0)?.rate
                    val amountFrom = state.value.amountFrom.toDoubleOrNull()
                    if (rate != null && amountFrom != null) {
                        val amountTo = amountFrom * rate
                        _state.update { it.copy(amountTo = amountTo.toString()) }
                    }
                } else {
                    _error.emit("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _error.emit("Error: ${e.message}")
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                val response = useCases.getCurrencies()
                if (response.isSuccessful) {
                    val currencies = response.body()?.map { it.code }?.sorted() ?: emptyList()
                    _state.update { it.copy(currencyList = currencies) }
                } else {
                    _error.emit("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _error.emit("Error: ${e.message}")
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun selectCurrency(currency: String, currencyFrom: Boolean) {
        _state.update { current ->
            current.copy(
                currencyFrom = if (currencyFrom) currency else current.currencyFrom,
                currencyTo = if (!currencyFrom) currency else current.currencyTo,
            )
        }
    }
}


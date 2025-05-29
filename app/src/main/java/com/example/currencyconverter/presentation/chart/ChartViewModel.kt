package com.example.currencyconverter.presentation.chart

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.CurrencyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val useCases: CurrencyUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(ChartState())
    val state: StateFlow<ChartState> = _state

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    private var historicalRatesJob: Job? = null


    @RequiresApi(Build.VERSION_CODES.O)
    fun onIntent(intent: ChartIntent) {
        when (intent) {
            is ChartIntent.SwapCurrencies -> {
                swapCurrencies()
            }

            is ChartIntent.LoadCurrencies -> {
                getCurrencies()
            }

            is ChartIntent.SelectCurrency -> {
                selectCurrency(intent.currency, intent.currencyFrom)
            }

            is ChartIntent.GetHistoricalRates -> {
                getHistoricalRates(state.value.timePeriod)
            }

            is ChartIntent.SetTimePeriod -> {
                setTimePeriod(intent.period)
            }
        }
    }

    private fun setTimePeriod(period: String) {
        _state.update { it.copy(timePeriod = period) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getPastDates(period: String): List<String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now().minusDays(1)
        val daysBack = when (period) {
            "48H" -> 2
            "3D" -> 3
            "5D" -> 5
            "1W" -> 7
            "2W" -> 14
            else -> {
                Log.e("ChartViewModel", "Unknown period: $period")
                return emptyList()
            }
        }
        return (0 until daysBack).map {
            today.minusDays(it.toLong()).format(formatter)
        }.reversed()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getHistoricalRates(period: String) {
        val dates = getPastDates(period)
        val limitedDispatcher = Dispatchers.IO.limitedParallelism(5)
        historicalRatesJob?.cancel()

        historicalRatesJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val deferredRates = dates.map { date ->
                    async(limitedDispatcher) {
                        try {
                            Log.d("ChartViewModel", "Fetching rate for date: $date")
                            val response = useCases.getHistoricalRates(
                                date = date,
                                baseCurrency = state.value.currencyFrom,
                                currencies = listOf(state.value.currencyTo)
                            )

                            if (response.isSuccessful) {
                                val rate =
                                    response.body()?.data?.get(date)?.get(state.value.currencyTo)
                                Log.d("ChartViewModel", "Rate for $date: $rate")
                                rate?.let { date to it }
                            } else {
                                Log.e(
                                    "ChartViewModel",
                                    "Error for $date: ${response.errorBody()?.string()}"
                                )
                                null
                            }
                        } catch (e: Exception) {
                            Log.e("ChartViewModel", "Exception for $date: ${e.message}")
                            null
                        }
                    }
                }

                val historicalRates = deferredRates.awaitAll().filterNotNull()

                _state.update {
                    it.copy(
                        historicalRates = historicalRates,
                        conversionRate = historicalRates.lastOrNull()?.second ?: 1.0,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                if (e is CancellationException) {
                    Log.d("ChartViewModel", "Historical rates Job cancelled.")
                } else {
                    _error.emit("Error: ${e.message}")
                }
                _state.update { it.copy(isLoading = false) }
            }
        }
    }


    private fun swapCurrencies() {
        _state.update { current ->
            current.copy(
                currencyFrom = current.currencyTo, currencyTo = current.currencyFrom
            )
        }
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                val response = useCases.getCurrencies()
                if (response.isSuccessful) {
                    val currencies = response.body()?.sortedBy { it.code } ?: emptyList()
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
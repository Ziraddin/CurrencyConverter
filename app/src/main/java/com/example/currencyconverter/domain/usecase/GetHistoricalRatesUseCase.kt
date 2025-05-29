package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.data.remote.model.HistoricalRatesResponse
import com.example.currencyconverter.domain.repository.CurrencyRepository
import retrofit2.Response
import javax.inject.Inject

class GetHistoricalRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        date: String,
        baseCurrency: String? = null,
        currencies: List<String>? = null
    ): Response<HistoricalRatesResponse> {
        return repository.getHistoricalRates(date, baseCurrency, currencies)
    }
}

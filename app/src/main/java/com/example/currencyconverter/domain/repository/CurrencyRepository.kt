package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.ExchangeRate
import com.example.currencyconverter.domain.model.Status
import retrofit2.Response

interface CurrencyRepository {

    suspend fun getStatus(): Response<Status>

    suspend fun getCurrencies(currencies: List<String>? = null): Response<List<Currency>>

    suspend fun getLatestRates(
        baseCurrency: String? = null,
        currencies: List<String>? = null
    ): Response<List<ExchangeRate>>
}
package com.example.currencyconverter.domain.usecase

data class CurrencyUseCases(
    val getStatus: GetStatusUseCase,
    val getCurrencies: GetCurrenciesUseCase,
    val getLatestRates: GetLatestRatesUseCase
)

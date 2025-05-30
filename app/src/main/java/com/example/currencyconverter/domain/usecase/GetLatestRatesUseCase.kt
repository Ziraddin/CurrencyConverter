package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetLatestRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        baseCurrency: String?, currencies: List<String>? = null, amount: Double?
    ) = if (amount != null && amount > 0) {
        repository.getLatestRates(baseCurrency, currencies)
    } else {
        throw IllegalArgumentException("Amount must be greater than 0")
    }
}

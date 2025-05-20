package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(currencies: List<String>? = null) =
        repository.getCurrencies(currencies)
}

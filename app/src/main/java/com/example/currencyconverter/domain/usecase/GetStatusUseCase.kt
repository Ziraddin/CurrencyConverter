package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetStatusUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke() = repository.getStatus()
}

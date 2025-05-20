package com.example.currencyconverter.presentation.core.di

import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.domain.usecase.CurrencyUseCases
import com.example.currencyconverter.domain.usecase.GetCurrenciesUseCase
import com.example.currencyconverter.domain.usecase.GetLatestRatesUseCase
import com.example.currencyconverter.domain.usecase.GetStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCurrencyUseCases(repository: CurrencyRepository): CurrencyUseCases {
        return CurrencyUseCases(
            getStatus = GetStatusUseCase(repository),
            getCurrencies = GetCurrenciesUseCase(repository),
            getLatestRates = GetLatestRatesUseCase(repository)
        )
    }
}

package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.remote.ICurrencyApi
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.ExchangeRate
import com.example.currencyconverter.domain.model.Status
import com.example.currencyconverter.domain.repository.CurrencyRepository
import retrofit2.Response
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: ICurrencyApi,
) : CurrencyRepository {
    override suspend fun getStatus(): Response<Status> {
        val response = api.getStatus()
        return Response.success(response.body()?.toDomainModel()) ?: Response.error(
            response.code(), response.errorBody() ?: error("Error body is null")
        )
    }

    override suspend fun getCurrencies(currencies: List<String>?): Response<List<Currency>> {
        val currenciesString = currencies?.joinToString(",")
        val response = api.getCurrencies(currenciesString)
        return Response.success(response.body()?.toDomainModel()) ?: Response.error(
            response.code(), response.errorBody() ?: error("Error body is null")
        )
    }

    override suspend fun getLatestRates(
        baseCurrency: String?, currencies: List<String>?
    ): Response<List<ExchangeRate>> {
        val currenciesString = currencies?.joinToString(",")
        val response = api.getLatestRates(baseCurrency, currenciesString)
        return Response.success(response.body()?.toDomainModel()) ?: Response.error(
            response.code(), response.errorBody() ?: error("Error body is null")
        )
    }
}
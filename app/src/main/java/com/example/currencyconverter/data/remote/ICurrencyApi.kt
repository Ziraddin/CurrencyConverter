package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.remote.model.CurrenciesResponse
import com.example.currencyconverter.data.remote.model.LatestRatesResponse
import com.example.currencyconverter.data.remote.model.StatusResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ICurrencyApi {

    @GET("status")
    suspend fun getStatus(): Response<StatusResponse>

    @GET("currencies")
    suspend fun getCurrencies(
        @Query("currencies") currencies: String? = null
    ): Response<CurrenciesResponse>

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base_currency") baseCurrency: String? = null,
        @Query("currencies") currencies: String? = null
    ): Response<LatestRatesResponse>
}

package com.example.currencyconverter.data.remote.model

import com.example.currencyconverter.domain.model.ExchangeRate
import kotlinx.serialization.Serializable

@Serializable
data class LatestRatesResponse(
    val data: Map<String, Double>
) {
    fun toDomainModel(): List<ExchangeRate> {
        return data.map { (currencyCode, rate) ->
            ExchangeRate(currencyCode, rate)
        }
    }
}

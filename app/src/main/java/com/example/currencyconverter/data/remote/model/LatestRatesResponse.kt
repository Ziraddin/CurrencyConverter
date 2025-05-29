package com.example.currencyconverter.data.remote.model

import android.os.Parcelable
import com.example.currencyconverter.domain.model.ExchangeRate
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatestRatesResponse(
    val data: Map<String, Double>
) : Parcelable {
    fun toDomainModel(): List<ExchangeRate> {
        return data.map { (currencyCode, rate) ->
            ExchangeRate(currencyCode, rate)
        }
    }
}

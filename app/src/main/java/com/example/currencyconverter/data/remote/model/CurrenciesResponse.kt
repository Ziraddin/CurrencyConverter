package com.example.currencyconverter.data.remote.model

import android.os.Parcelable
import com.example.currencyconverter.domain.model.Currency
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrenciesResponse(
    val data: Map<String, CurrencyDetail>
) : Parcelable {
    fun toDomainModel(): List<Currency> {
        return data.map { (code, currencyDetail) ->
            Currency(
                code = code,
                name = currencyDetail.name,
                symbol = currencyDetail.symbol,
                symbolNative = currencyDetail.symbol_native,
                decimalDigits = currencyDetail.decimal_digits,
                rounding = currencyDetail.rounding,
                namePlural = currencyDetail.name_plural
            )
        }.toList()
    }
}

@Parcelize
data class CurrencyDetail(
    val symbol: String,
    val name: String,
    val symbol_native: String,
    val decimal_digits: Int,
    val rounding: Int,
    val code: String,
    val name_plural: String
) : Parcelable

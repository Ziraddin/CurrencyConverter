package com.example.currencyconverter.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoricalRatesResponse(
    @SerializedName("data")
    val data: Map<String, Map<String, Double>>
) : Parcelable

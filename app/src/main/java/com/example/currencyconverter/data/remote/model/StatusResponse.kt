package com.example.currencyconverter.data.remote.model

import android.os.Parcelable
import com.example.currencyconverter.domain.model.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusResponse(
    val quotas: Quotas
) : Parcelable {
    fun toDomainModel(): Status {
        return Status(
            monthlyTotal = quotas.month.total,
            monthlyUsed = quotas.month.used,
            monthlyRemaining = quotas.month.remaining
        )
    }
}

@Parcelize
data class Quotas(
    val month: MonthQuota
) : Parcelable

@Parcelize
data class MonthQuota(
    val total: Int,
    val used: Int,
    val remaining: Int
) : Parcelable

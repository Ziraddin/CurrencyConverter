package com.example.currencyconverter.domain.model

data class Status(
    val monthlyTotal: Int,
    val monthlyUsed: Int,
    val monthlyRemaining: Int
)

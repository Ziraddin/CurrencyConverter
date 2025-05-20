package com.example.currencyconverter.domain.model

data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    val symbolNative: String,
    val decimalDigits: Int,
    val rounding: Int,
    val namePlural: String
)


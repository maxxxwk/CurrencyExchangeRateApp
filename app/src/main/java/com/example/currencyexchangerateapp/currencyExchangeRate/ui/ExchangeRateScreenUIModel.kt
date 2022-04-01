package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.Currency

data class ExchangeRateScreenUIModel(
    val currencies: List<Currency>,
    val from: String?,
    val to: String?,
    val amount: String
)

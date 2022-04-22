package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.Currency

sealed interface ExchangeRateScreenState {
    object Loading : ExchangeRateScreenState
    data class Error(val message: String) : ExchangeRateScreenState
    data class Content(
        val currencies: List<Currency>,
        val from: String?,
        val to: String?,
        val exchangeRate: String,
        val amount: String
    ) : ExchangeRateScreenState {
        val isSelectedCurrencies: Boolean
            get() = from != null && to != null
    }
}

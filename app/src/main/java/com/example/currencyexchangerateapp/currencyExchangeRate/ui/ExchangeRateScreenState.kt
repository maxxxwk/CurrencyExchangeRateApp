package com.example.currencyexchangerateapp.currencyExchangeRate.ui

sealed interface ExchangeRateScreenState {
    object Loading : ExchangeRateScreenState
    data class Error(val message: String) : ExchangeRateScreenState
    data class Content(val exchangeRateScreenUIModel: ExchangeRateScreenUIModel) :
        ExchangeRateScreenState
}
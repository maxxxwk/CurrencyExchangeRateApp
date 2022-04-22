package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.Currency
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.ExchangeRate

sealed interface ExchangeRateScreenIntent {
    object LoadCurrencies : ExchangeRateScreenIntent
    data class LoadedCurrencies(val currencies: List<Currency>) : ExchangeRateScreenIntent
    data class SelectCurrencyFrom(val selectedCurrency: String) : ExchangeRateScreenIntent
    data class SelectCurrencyTo(val selectedCurrency: String) : ExchangeRateScreenIntent
    data class EnterAmount(val amount: String) : ExchangeRateScreenIntent
    data class LoadExchangeRate(val amount: Double) : ExchangeRateScreenIntent
    data class LoadedExchangeRate(val exchangeRate: ExchangeRate) : ExchangeRateScreenIntent
    data class ShowError(val message: String) : ExchangeRateScreenIntent
}

package com.example.currencyexchangerateapp.currencyExchangeRate.ui

sealed class NavigationRoutes(val route: String) {
    object ExchangeRateScreen: NavigationRoutes("exchangeRateScreen")
}
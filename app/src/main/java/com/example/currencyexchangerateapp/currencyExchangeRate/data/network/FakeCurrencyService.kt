package com.example.currencyexchangerateapp.currencyExchangeRate.data.network

import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.CurrencyListResponse
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.CurrencyResponse
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.ExchangeRateResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeCurrencyService : CurrencyService {

    private val exchangeRateCache = mutableMapOf<Pair<String, String>, Double>()

    override suspend fun getCurrencyList(): CurrencyListResponse = coroutineScope {
        delay(1500)
        return@coroutineScope CurrencyListResponse(
            errorCode = 0,
            currencies = listOf(
                CurrencyResponse(
                    code = "USD",
                    name = "United States Dollar"
                ),
                CurrencyResponse(
                    code = "AED",
                    name = "United Arab Emirates Dirham"
                ),
                CurrencyResponse(
                    code = "CAD",
                    name = "Canadian Dollar"
                ),
                CurrencyResponse(
                    code = "EUR",
                    name = "Euro"
                ),
                CurrencyResponse(
                    code = "PLN",
                    name = "Polish Zloty"
                ),
                CurrencyResponse(
                    code = "RUB",
                    name = "Russian Ruble"
                ),
                CurrencyResponse(
                    code = "UAH",
                    name = "Ukrainian Hryvnia"
                )
            )
        )
    }

    override suspend fun getExchangeRate(
        from: String,
        to: String,
        amount: Double
    ): ExchangeRateResponse = coroutineScope {
        delay(800)
        val exchangeRate = exchangeRateCache[Pair(from, to)]
        exchangeRate?.let {
            return@coroutineScope ExchangeRateResponse(
                errorCode = 0,
                amount = amount * it
            )
        }
        val generatedExchangeRate = Random.nextDouble(0.2, 120.0)
        exchangeRateCache[Pair(from, to)] = generatedExchangeRate
        return@coroutineScope ExchangeRateResponse(
            errorCode = 0,
            amount = generatedExchangeRate * amount
        )
    }
}
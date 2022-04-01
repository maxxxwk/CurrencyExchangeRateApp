package com.example.currencyexchangerateapp.currencyExchangeRate.data.network

import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.CurrencyListResponse
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("api/currency_list.php")
    suspend fun getCurrencyList(): CurrencyListResponse

    @GET("api/currency.php")
    suspend fun getExchangeRate(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): ExchangeRateResponse
}
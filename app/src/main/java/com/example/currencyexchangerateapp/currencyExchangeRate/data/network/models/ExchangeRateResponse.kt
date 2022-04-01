package com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateResponse(
    @SerialName("error") val errorCode: Int?,
    @SerialName("amount") val amount: Double?
)

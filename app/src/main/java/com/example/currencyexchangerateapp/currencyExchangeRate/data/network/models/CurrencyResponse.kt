package com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    @SerialName("currency") val code: String?,
    @SerialName("description") val name: String?
)

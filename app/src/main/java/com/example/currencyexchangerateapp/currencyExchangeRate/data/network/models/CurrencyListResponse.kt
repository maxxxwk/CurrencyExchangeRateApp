package com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyListResponse(
    @SerialName("error") val errorCode: Int?,
    @SerialName("currencies") val currencies: List<CurrencyResponse>?
)

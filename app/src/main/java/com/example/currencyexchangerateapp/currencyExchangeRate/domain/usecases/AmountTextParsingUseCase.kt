package com.example.currencyexchangerateapp.currencyExchangeRate.domain.usecases

import javax.inject.Inject

class AmountTextParsingUseCase @Inject constructor() {
    operator fun invoke(amountText: String): Double? {
        return amountText.toIntOrNull()?.toDouble() ?: amountText.toDoubleOrNull()
    }
}
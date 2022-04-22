package com.example.currencyexchangerateapp.currencyExchangeRate.domain.usecases

import javax.inject.Inject

class AmountTextValidationUseCase @Inject constructor() {
    operator fun invoke(amountText: String): String {
        var newText = amountText.replace(',', '.')
            .filter { it != ' ' && it != '-' }
        val pointIndex = newText.indexOf('.')
        if (pointIndex != -1) {
            if (newText.count { it == '.' } > 1) {
                newText = newText.filterIndexed { index, c ->
                    !(c == '.' && index != pointIndex)
                }
            }
            if (newText.length > (pointIndex + 1) + MAX_ALLOWABLE_DIGITS_COUNT_AFTER_POINT) {
                newText = newText.substring(
                    0, (pointIndex + 1) + MAX_ALLOWABLE_DIGITS_COUNT_AFTER_POINT
                )
            }
        }
        return newText
    }

    private companion object {
        const val MAX_ALLOWABLE_DIGITS_COUNT_AFTER_POINT = 2
    }
}

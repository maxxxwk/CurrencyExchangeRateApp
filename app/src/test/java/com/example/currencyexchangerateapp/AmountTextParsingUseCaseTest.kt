package com.example.currencyexchangerateapp

import com.example.currencyexchangerateapp.currencyExchangeRate.domain.usecases.AmountTextParsingUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class AmountTextParsingUseCaseTest {
    private val amountTextParsingUseCase = AmountTextParsingUseCase()

    @Test
    fun `Given result when text is correct int number`() {
        val amountText = "23131"
        assertEquals(amountText.toInt().toDouble(), amountTextParsingUseCase(amountText))
    }

    @Test
    fun `Given result when text is correct double number`() {
        val amountText = "23131.054"
        assertEquals(amountText.toDouble(), amountTextParsingUseCase(amountText))
    }

    @Test
    fun `Given result when text is not correct number`() {
        val amountText = "2sdhjfjkdshfjk32423"
        assertEquals(null, amountTextParsingUseCase(amountText))
    }
}

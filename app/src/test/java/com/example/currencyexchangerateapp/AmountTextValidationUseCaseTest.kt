package com.example.currencyexchangerateapp

import com.example.currencyexchangerateapp.currencyExchangeRate.domain.usecases.AmountTextValidationUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class AmountTextValidationUseCaseTest {

    private val amountTextValidationUseCase = AmountTextValidationUseCase()

    @Test
    fun `Given result with text is valid double number`() {
        val amountText = "1.0"
        assertEquals(amountText, amountTextValidationUseCase(amountText))
    }

    @Test
    fun `Given result with text is valid int number`() {
        val amountText = "1"
        assertEquals(amountText, amountTextValidationUseCase(amountText))
    }

    @Test
    fun `Given result when in text more then 2 digits after point`() {
        val amountText = "1.234234"
        val expected = "1.23"
        assertEquals(expected, amountTextValidationUseCase(amountText))
    }

    @Test
    fun `Given result when in text more then 2 points`() {
        val amountText = "1.2.34234"
        val expected = "1.23"
        assertEquals(expected, amountTextValidationUseCase(amountText))
    }

    @Test
    fun `Given result when used space char or '-' `() {
        val amountText = "1.24-   -"
        val expected = "1.24"
        assertEquals(expected, amountTextValidationUseCase(amountText))
    }
}

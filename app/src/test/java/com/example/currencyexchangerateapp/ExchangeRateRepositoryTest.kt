package com.example.currencyexchangerateapp

import com.example.currencyexchangerateapp.common.resurces.ResourceManager
import com.example.currencyexchangerateapp.currencyExchangeRate.data.ExchangeRateRepository
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.CurrencyService
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.ExchangeRateResponse
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.ExchangeRate
import com.example.currencyexchangerateapp.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class ExchangeRateRepositoryTest {

    private val testCoroutineScheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(testCoroutineScheduler)

    @Test
    fun `Given result when service throws Exception`() {
        val errorMessage = "Error!!!"
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getExchangeRate(any(), any(), any()) } throws Exception()
        }
        val resourceManagerMock = mockk<ResourceManager> {
            every { getString(any()) } returns errorMessage
        }

        val testExchangeRateRepository =
            ExchangeRateRepository(currencyServiceMock, resourceManagerMock, dispatcher)

        runTest(testCoroutineScheduler) {
            assertEquals(
                testExchangeRateRepository.getExchangeRate(
                    "UAH",
                    "USD",
                    1.0
                ), ResultWrapper.Fail(errorMessage)
            )
        }
    }

    @Test
    fun `Given result when errorCode is not 0`() {
        val errorMessage = "Error!!!"
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getExchangeRate(any(), any(), any()) } returns ExchangeRateResponse(1, null)
        }
        val resourceManagerMock = mockk<ResourceManager> {
            every { getString(any()) } returns errorMessage
        }

        val testExchangeRateRepository =
            ExchangeRateRepository(currencyServiceMock, resourceManagerMock, dispatcher)
        runTest(testCoroutineScheduler) {
            assertEquals(
                testExchangeRateRepository.getExchangeRate(
                    "UAH",
                    "USD",
                    1.0
                ), ResultWrapper.Fail(errorMessage)
            )
        }
    }

    @Test
    fun `Given result when amount is null`() {
        val errorMessage = "Error!!!"
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getExchangeRate(any(), any(), any()) } returns ExchangeRateResponse(0, null)
        }
        val resourceManagerMock = mockk<ResourceManager> {
            every { getString(any()) } returns errorMessage
        }

        val testExchangeRateRepository =
            ExchangeRateRepository(currencyServiceMock, resourceManagerMock, dispatcher)
        runTest(testCoroutineScheduler) {
            assertEquals(
                testExchangeRateRepository.getExchangeRate(
                    "UAH",
                    "USD",
                    1.0
                ), ResultWrapper.Fail(errorMessage)
            )
        }
    }

    @Test
    fun `Given result when response without error`() {
        val response = ExchangeRateResponse(0, 0.2)
        val currencyFrom = "UAH"
        val currencyTo = "USD"
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getExchangeRate(any(), any(), any()) } returns response
        }
        val resourceManagerMock = mockk<ResourceManager> {
            every { getString(any()) } returns ""
        }

        val testExchangeRateRepository =
            ExchangeRateRepository(currencyServiceMock, resourceManagerMock, dispatcher)
        runTest(testCoroutineScheduler) {
            assertEquals(
                testExchangeRateRepository.getExchangeRate(
                    currencyFrom,
                    currencyTo,
                    1.0
                ),
                ResultWrapper.Success(
                    ExchangeRate(
                        currencyFrom,
                        currencyTo,
                        "0,20"
                    )
                )
            )
        }
    }
}

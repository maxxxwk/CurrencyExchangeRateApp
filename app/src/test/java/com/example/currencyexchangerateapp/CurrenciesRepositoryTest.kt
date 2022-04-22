package com.example.currencyexchangerateapp

import android.util.Log
import com.example.currencyexchangerateapp.common.resurces.ResourceManager
import com.example.currencyexchangerateapp.currencyExchangeRate.data.CurrenciesRepository
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrenciesDao
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrencyEntity
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.CurrencyService
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.CurrencyListResponse
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.models.CurrencyResponse
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.Currency
import com.example.currencyexchangerateapp.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class CurrenciesRepositoryTest {
    private val testCoroutineScheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(testCoroutineScheduler)

    @Test
    fun `Given result when db returns currencies`() {
        val currencies = listOf(Currency("UAH", "Hryvnia"), Currency("EUR", "Euro"))
        val currencyEntities = currencies.map { CurrencyEntity(it.code, it.name) }
        val currenciesDaoMock = mockk<CurrenciesDao> {
            coEvery { getCurrencies() } returns currencyEntities
        }
        val currencyServiceMock = mockk<CurrencyService>()
        val resourceManagerMock = mockk<ResourceManager>()

        val testCurrenciesRepository = CurrenciesRepository(
            currenciesDaoMock,
            currencyServiceMock,
            resourceManagerMock,
            dispatcher
        )
        runTest(testCoroutineScheduler) {
            assertEquals(
                testCurrenciesRepository.getCurrencies(),
                ResultWrapper.Success(currencies)
            )
        }
    }

    @Test
    fun `Given result when db throws exception but service return currencies`() {
        val currencies = listOf(Currency("UAH", "Hryvnia"), Currency("EUR", "Euro"))
        val currencyListResponse = CurrencyListResponse(
            0,
            currencies.map {
                CurrencyResponse(
                    it.code,
                    it.name
                )
            }
        )
        val currenciesDaoMock = mockk<CurrenciesDao> {
            coEvery { getCurrencies() } throws Exception()
            coEvery { insertCurrencies(any()) } returns Unit
        }
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getCurrencyList() } returns currencyListResponse
        }
        val resourceManagerMock = mockk<ResourceManager>()

        val testCurrenciesRepository = CurrenciesRepository(
            currenciesDaoMock,
            currencyServiceMock,
            resourceManagerMock,
            dispatcher
        )
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        runTest(testCoroutineScheduler) {
            assertEquals(
                testCurrenciesRepository.getCurrencies(),
                ResultWrapper.Success(currencies)
            )
        }
    }

    @Test
    fun `Given result when db returns empty list but service return currencies`() {
        val currencies = listOf(Currency("UAH", "Hryvnia"), Currency("EUR", "Euro"))
        val currencyListResponse = CurrencyListResponse(
            0,
            currencies.map {
                CurrencyResponse(
                    it.code,
                    it.name
                )
            }
        )
        val currenciesDaoMock = mockk<CurrenciesDao> {
            coEvery { getCurrencies() } returns  emptyList()
            coEvery { insertCurrencies(any()) } returns Unit
        }
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getCurrencyList() } returns currencyListResponse
        }
        val resourceManagerMock = mockk<ResourceManager>()

        val testCurrenciesRepository = CurrenciesRepository(
            currenciesDaoMock,
            currencyServiceMock,
            resourceManagerMock,
            dispatcher
        )
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        runTest(testCoroutineScheduler) {
            assertEquals(
                testCurrenciesRepository.getCurrencies(),
                ResultWrapper.Success(currencies)
            )
        }
    }

    @Test
    fun `Given result when loading from db and network both throw exception`() {
        val errorMessage = "Error"
        val currenciesDaoMock = mockk<CurrenciesDao> {
            coEvery { getCurrencies() } throws Exception()
        }
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getCurrencyList() } throws Exception()
        }
        val resourceManagerMock = mockk<ResourceManager> {
            every { getString(any()) } returns errorMessage
        }

        val testCurrenciesRepository = CurrenciesRepository(
            currenciesDaoMock,
            currencyServiceMock,
            resourceManagerMock,
            dispatcher
        )
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        runTest(testCoroutineScheduler) {
            assertEquals(
                testCurrenciesRepository.getCurrencies(),
                ResultWrapper.Fail(errorMessage)
            )
        }
    }

    @Test
    fun `Given result when db throws exception and service returns response with currencies null`() {
        val currencyListResponse = CurrencyListResponse(0, null)
        val errorMessage = "Error"
        val currenciesDaoMock = mockk<CurrenciesDao> {
            coEvery { getCurrencies() } throws Exception()
        }
        val currencyServiceMock = mockk<CurrencyService> {
            coEvery { getCurrencyList() } returns currencyListResponse
        }
        val resourceManagerMock = mockk<ResourceManager> {
            every { getString(any()) } returns errorMessage
        }

        val testCurrenciesRepository = CurrenciesRepository(
            currenciesDaoMock,
            currencyServiceMock,
            resourceManagerMock,
            dispatcher
        )
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        runTest(testCoroutineScheduler) {
            assertEquals(
                testCurrenciesRepository.getCurrencies(),
                ResultWrapper.Fail(errorMessage)
            )
        }
    }
}

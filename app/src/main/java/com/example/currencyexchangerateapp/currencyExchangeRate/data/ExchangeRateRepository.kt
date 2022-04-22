package com.example.currencyexchangerateapp.currencyExchangeRate.data

import com.example.currencyexchangerateapp.R
import com.example.currencyexchangerateapp.common.resurces.ResourceManager
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.CurrencyService
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.ExchangeRate
import com.example.currencyexchangerateapp.di.DispatcherIO
import com.example.currencyexchangerateapp.di.FakeService
import com.example.currencyexchangerateapp.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor(
    @FakeService private val currencyService: CurrencyService,
    private val resourceManager: ResourceManager,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend fun getExchangeRate(
        from: String,
        to: String,
        amount: Double
    ): ResultWrapper<ExchangeRate> = withContext(dispatcher) {
        val exchangeRateResponse = try {
            currencyService.getExchangeRate(from, to, amount)
        } catch (e: Exception) {
            return@withContext ResultWrapper.Fail(resourceManager.getString(R.string.exchange_rate_loading_error_message))
        }
        if (exchangeRateResponse.errorCode == 0 && exchangeRateResponse.amount != null) {
            return@withContext ResultWrapper.Success(
                ExchangeRate(from, to, "%.2f".format(exchangeRateResponse.amount))
            )
        }
        ResultWrapper.Fail(
            when (exchangeRateResponse.errorCode) {
                400 -> resourceManager.getString(R.string.api_limit_error_message)
                else -> resourceManager.getString(R.string.unknown_error_message)
            }
        )
    }
}

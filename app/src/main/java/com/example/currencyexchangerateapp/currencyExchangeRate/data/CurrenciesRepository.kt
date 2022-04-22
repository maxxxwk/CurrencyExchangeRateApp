package com.example.currencyexchangerateapp.currencyExchangeRate.data

import android.util.Log
import com.example.currencyexchangerateapp.R
import com.example.currencyexchangerateapp.common.resurces.ResourceManager
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrenciesDao
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrencyEntity
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.CurrencyService
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.Currency
import com.example.currencyexchangerateapp.di.DispatcherIO
import com.example.currencyexchangerateapp.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class CurrenciesRepository @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val currencyService: CurrencyService,
    private val resourceManager: ResourceManager,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend fun getCurrencies(): ResultWrapper<List<Currency>> = withContext(dispatcher) {
        loadCurrenciesFromLocalDB().let { currencies ->
            if (currencies.isNotEmpty()) {
                return@withContext ResultWrapper.Success(
                    currencies.map { Currency(it.currencyCode, it.currencyName) }
                )
            }
        }
        val currenciesResponse = try {
            currencyService.getCurrencyList()
        } catch (e: Exception) {
            return@withContext ResultWrapper.Fail(resourceManager.getString(R.string.currencies_loading_error_message))
        }
        if (currenciesResponse.errorCode == 0 && !currenciesResponse.currencies.isNullOrEmpty()) {
            val currenciesFromRemoteSource = currenciesResponse.currencies
                .filter { !it.code.isNullOrBlank() && !it.name.isNullOrBlank() }
                .map { Currency(it.code!!, it.name!!) }
            if (currenciesFromRemoteSource.isNotEmpty()) {
                saveCurrenciesToLocalDB(currenciesFromRemoteSource)
                return@withContext ResultWrapper.Success(currenciesFromRemoteSource)
            }
        }
        return@withContext when (currenciesResponse.errorCode) {
            400 -> ResultWrapper.Fail(resourceManager.getString(R.string.api_limit_error_message))
            else -> ResultWrapper.Fail(resourceManager.getString(R.string.unknown_error_message))
        }
    }

    private suspend fun loadCurrenciesFromLocalDB(): List<CurrencyEntity> = coroutineScope {
        return@coroutineScope try {
            currenciesDao.getCurrencies()
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, e.message ?: "can't load currencies from local db")
            emptyList()
        }
    }

    private suspend fun saveCurrenciesToLocalDB(currencies: List<Currency>) = coroutineScope {
        launch {
            try {
                currenciesDao.insertCurrencies(
                    currencies = currencies.map { CurrencyEntity(it.code, it.name) }
                )
            } catch (e: Exception) {
                Log.e(this::class.java.simpleName, e.message ?: "can't save currencies to local db")
            }
        }
    }
}

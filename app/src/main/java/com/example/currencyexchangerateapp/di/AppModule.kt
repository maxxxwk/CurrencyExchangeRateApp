package com.example.currencyexchangerateapp.di

import com.example.currencyexchangerateapp.common.resurces.ResourceManager
import com.example.currencyexchangerateapp.currencyExchangeRate.data.CurrenciesRepository
import com.example.currencyexchangerateapp.currencyExchangeRate.data.ExchangeRateRepository
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrenciesDao
import com.example.currencyexchangerateapp.currencyExchangeRate.data.network.CurrencyService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkModule::class, DBModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideCurrenciesRepository(
        currencyService: CurrencyService,
        currenciesDao: CurrenciesDao,
        resourceManager: ResourceManager
    ): CurrenciesRepository {
        return CurrenciesRepository(
            currenciesDao = currenciesDao,
            currencyService = currencyService,
            resourceManager = resourceManager,
            dispatcher = Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun provideExchangeRateRepository(
        currencyService: CurrencyService,
        resourceManager: ResourceManager
    ): ExchangeRateRepository {
        return ExchangeRateRepository(
            currencyService = currencyService,
            resourceManager = resourceManager,
            dispatcher = Dispatchers.IO
        )
    }

}
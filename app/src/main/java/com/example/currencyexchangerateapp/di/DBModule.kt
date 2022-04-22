package com.example.currencyexchangerateapp.di

import android.content.Context
import androidx.room.Room
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrenciesDB
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrenciesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    fun provideCurrenciesDao(currenciesDB: CurrenciesDB): CurrenciesDao {
        return currenciesDB.currenciesDao()
    }

    @Provides
    @Singleton
    fun provideCurrenciesDB(context: Context): CurrenciesDB {
        return Room.databaseBuilder(context, CurrenciesDB::class.java, "currencies-db").build()
    }
}

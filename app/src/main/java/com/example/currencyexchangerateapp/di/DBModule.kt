package com.example.currencyexchangerateapp.di

import android.content.Context
import androidx.room.Room
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrenciesDB
import com.example.currencyexchangerateapp.currencyExchangeRate.data.db.CurrenciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    fun provideCurrenciesDao(currenciesDB: CurrenciesDB): CurrenciesDao {
        return currenciesDB.currenciesDao()
    }

    @Provides
    fun provideCurrenciesDB(@ApplicationContext context: Context): CurrenciesDB {
        return Room.databaseBuilder(context, CurrenciesDB::class.java, "currencies-db").build()
    }
}

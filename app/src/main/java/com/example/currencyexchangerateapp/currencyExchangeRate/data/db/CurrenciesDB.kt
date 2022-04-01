package com.example.currencyexchangerateapp.currencyExchangeRate.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class CurrenciesDB : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao
}
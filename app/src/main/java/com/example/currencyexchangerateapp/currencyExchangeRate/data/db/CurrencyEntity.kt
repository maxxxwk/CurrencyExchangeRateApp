package com.example.currencyexchangerateapp.currencyExchangeRate.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "currency_code")
    val currencyCode: String,
    @ColumnInfo(name = "currency_name")
    val currencyName: String
)

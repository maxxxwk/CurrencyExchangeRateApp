package com.example.currencyexchangerateapp.utils

sealed interface ResultWrapper<out T> {
    data class Success<T>(val value: T) : ResultWrapper<T>
    data class Fail(val errorMessage: String) : ResultWrapper<Nothing>
}

package com.example.currencyexchangerateapp.utils

import android.content.Context
import com.example.currencyexchangerateapp.App
import com.example.currencyexchangerateapp.di.AppComponent

fun Context.getAppComponent(): AppComponent = when (this) {
    is App -> appComponent
    else -> this.applicationContext.getAppComponent()
}
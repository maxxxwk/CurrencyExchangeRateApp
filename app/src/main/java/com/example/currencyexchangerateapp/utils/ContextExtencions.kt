package com.example.currencyexchangerateapp.utils

import android.content.Context
import com.example.currencyexchangerateapp.App
import com.example.currencyexchangerateapp.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

package com.example.currencyexchangerateapp

import android.app.Application
import com.example.currencyexchangerateapp.di.AppComponent
import com.example.currencyexchangerateapp.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appContext(this)
            .build()
    }
}

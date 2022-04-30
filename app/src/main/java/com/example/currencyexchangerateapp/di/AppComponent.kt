package com.example.currencyexchangerateapp.di

import android.content.Context
import com.example.currencyexchangerateapp.currencyExchangeRate.ui.ExchangeRateScreenViewModel
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    @FlowPreview
    fun getExchangeRateScreenViewModel(): ExchangeRateScreenViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}

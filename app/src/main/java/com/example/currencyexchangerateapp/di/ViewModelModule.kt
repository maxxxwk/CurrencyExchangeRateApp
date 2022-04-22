package com.example.currencyexchangerateapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyexchangerateapp.common.viewModel.ViewModelFactory
import com.example.currencyexchangerateapp.common.viewModel.ViewModelKey
import com.example.currencyexchangerateapp.currencyExchangeRate.ui.ExchangeRateScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.FlowPreview

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @FlowPreview
    @Binds
    @[IntoMap ViewModelKey(ExchangeRateScreenViewModel::class)]
    abstract fun bindExchangeRateScreenViewModel(exchangeRateScreenViewModel: ExchangeRateScreenViewModel): ViewModel
}

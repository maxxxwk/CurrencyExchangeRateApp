package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.currencyexchangerateapp.currencyExchangeRate.ui.ExchangeRateScreenState.*
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun ExchangeRateScreen(exchangeRateScreenViewModel: ExchangeRateScreenViewModel) {
    val state = exchangeRateScreenViewModel.state.collectAsState()
    when (val value = state.value) {
        Loading -> ExchangeRateScreenLoading()
        is Error -> ExchangeRateScreenError(errorMessage = value.message)
        is Content -> ExchangeRateScreenContent(exchangeRateScreenUIModel = value.exchangeRateScreenUIModel)
    }
}

@Composable
fun ExchangeRateScreenLoading() {

}

@Composable
fun ExchangeRateScreenError(errorMessage: String) {

}

@Composable
fun ExchangeRateScreenContent(exchangeRateScreenUIModel: ExchangeRateScreenUIModel) {

}
package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import kotlinx.coroutines.FlowPreview

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@FlowPreview
@Composable
fun ExchangeRateScreen(exchangeRateScreenViewModel: ExchangeRateScreenViewModel) {
    val state by exchangeRateScreenViewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        exchangeRateScreenViewModel.interact(
            ExchangeRateScreenIntent.LoadCurrencies
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (state) {
            ExchangeRateScreenState.Loading -> ExchangeRateScreenLoading()
            is ExchangeRateScreenState.Error -> ExchangeRateScreenError(
                errorMessage = (state as ExchangeRateScreenState.Error).message
            )
            is ExchangeRateScreenState.Content -> ExchangeRateScreenContent(
                content = state as ExchangeRateScreenState.Content,
                selectCurrencyFrom = {
                    exchangeRateScreenViewModel.interact(
                        ExchangeRateScreenIntent.SelectCurrencyFrom(it)
                    )
                },
                selectCurrencyTo = {
                    exchangeRateScreenViewModel.interact(
                        ExchangeRateScreenIntent.SelectCurrencyTo(it)
                    )
                },
                onAmountEntered = {
                    exchangeRateScreenViewModel.interact(
                        ExchangeRateScreenIntent.EnterAmount(it)
                    )
                }
            )
        }
    }
}

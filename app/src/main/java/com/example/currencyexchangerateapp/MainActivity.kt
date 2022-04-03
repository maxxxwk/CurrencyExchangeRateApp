package com.example.currencyexchangerateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyexchangerateapp.currencyExchangeRate.ui.ExchangeRateScreen
import com.example.currencyexchangerateapp.ui.theme.CurrencyExchangeRateAppTheme
import com.example.currencyexchangerateapp.utils.getAppComponent
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this)
        setContent {
            CurrencyExchangeRateAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = EXCHANGE_RATE_SCREEN
                    ) {
                        composable(route = EXCHANGE_RATE_SCREEN) {
                            ExchangeRateScreen(
                                exchangeRateScreenViewModel = viewModel(factory = viewModelFactory)
                            )
                        }
                    }
                }
            }
        }
    }

    private companion object {
        const val EXCHANGE_RATE_SCREEN = "EXCHANGE_RATE_SCREEN"
    }
}



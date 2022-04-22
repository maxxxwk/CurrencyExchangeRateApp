package com.example.currencyexchangerateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

@ExperimentalMaterialApi
@FlowPreview
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this)
        setContent {
            CurrencyExchangeRateAppTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    color = MaterialTheme.colors.primary
                                )
                            },
                            backgroundColor = MaterialTheme.colors.secondary
                        )
                    }
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoutes.EXCHANGE_RATE_SCREEN
                    ) {
                        composable(NavigationRoutes.EXCHANGE_RATE_SCREEN) {
                            ExchangeRateScreen(exchangeRateScreenViewModel = viewModel(factory = viewModelFactory))
                        }
                    }
                }
            }
        }
    }
}

object NavigationRoutes {
    const val EXCHANGE_RATE_SCREEN = "EXCHANGE_RATE_SCREEN"
}

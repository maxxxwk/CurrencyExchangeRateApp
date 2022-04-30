package com.example.currencyexchangerateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyexchangerateapp.currencyExchangeRate.ui.ExchangeRateScreen
import com.example.currencyexchangerateapp.currencyExchangeRate.ui.NavigationRoutes
import com.example.currencyexchangerateapp.ui.theme.CurrencyExchangeRateAppTheme
import com.example.currencyexchangerateapp.utils.appComponent
import com.example.currencyexchangerateapp.utils.initViewModel
import kotlinx.coroutines.FlowPreview

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@FlowPreview
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        startDestination = NavigationRoutes.ExchangeRateScreen.route
                    ) {
                        composable(NavigationRoutes.ExchangeRateScreen.route) {
                            ExchangeRateScreen(initViewModel(appComponent::getExchangeRateScreenViewModel))
                        }
                    }
                }
            }
        }
    }
}

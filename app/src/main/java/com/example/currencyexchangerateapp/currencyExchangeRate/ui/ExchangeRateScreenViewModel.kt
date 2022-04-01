package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import androidx.lifecycle.viewModelScope
import com.example.currencyexchangerateapp.R
import com.example.currencyexchangerateapp.common.ToastManager
import com.example.currencyexchangerateapp.common.resurces.ResourceManager
import com.example.currencyexchangerateapp.common.viewModel.BaseViewModel
import com.example.currencyexchangerateapp.currencyExchangeRate.data.CurrenciesRepository
import com.example.currencyexchangerateapp.currencyExchangeRate.data.ExchangeRateRepository
import com.example.currencyexchangerateapp.utils.ResultWrapper
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
class ExchangeRateScreenViewModel @Inject constructor(
    private val currencyRepository: CurrenciesRepository,
    private val exchangeRateRepository: ExchangeRateRepository,
    private val resourceManager: ResourceManager,
    private val toastManager: ToastManager
) : BaseViewModel<ExchangeRateScreenState, ExchangeRateScreenIntent>() {
    override val initialState: ExchangeRateScreenState
        get() = ExchangeRateScreenState.Loading

    private val amountFieldValueState = MutableSharedFlow<String>(replay = 1)

    init {
        amountFieldValueState.debounce(DEBOUNCE_TIME)
            .map { it.toDouble() }
            .catch { interact(ExchangeRateScreenIntent.ShowWarningToast(resourceManager.getString(R.string.incorrect_amount_warning_text))) }
            .distinctUntilChanged()
            .onEach {
                interact(ExchangeRateScreenIntent.LoadExchangeRate(it))
            }.launchIn(viewModelScope)
    }

    override fun handleIntent(
        intent: ExchangeRateScreenIntent,
        oldSTATE: ExchangeRateScreenState
    ): ExchangeRateScreenState = when (intent) {
        ExchangeRateScreenIntent.LoadCurrencies -> {
            loadCurrencies()
            ExchangeRateScreenState.Loading
        }
        is ExchangeRateScreenIntent.EnterAmount -> {
            viewModelScope.launch { amountFieldValueState.emit(intent.amount) }
            oldSTATE
        }
        is ExchangeRateScreenIntent.LoadedCurrencies -> {
            ExchangeRateScreenState.Content(
                ExchangeRateScreenUIModel(intent.currencies, null, null, "")
            )
        }
        is ExchangeRateScreenIntent.LoadedExchangeRate -> {
            ExchangeRateScreenState.Content(
                (oldSTATE as ExchangeRateScreenState.Content).exchangeRateScreenUIModel
                    .copy(amount = intent.exchangeRate.amount)
            )
        }
        is ExchangeRateScreenIntent.SelectCurrencyFrom -> {
            ExchangeRateScreenState.Content(
                (oldSTATE as ExchangeRateScreenState.Content).exchangeRateScreenUIModel
                    .copy(from = intent.currency.code)
            )
        }
        is ExchangeRateScreenIntent.SelectCurrencyTo -> {
            ExchangeRateScreenState.Content(
                (oldSTATE as ExchangeRateScreenState.Content).exchangeRateScreenUIModel
                    .copy(to = intent.currency.code)
            )
        }
        is ExchangeRateScreenIntent.ShowError -> {
            ExchangeRateScreenState.Error(intent.message)
        }
        is ExchangeRateScreenIntent.LoadExchangeRate -> {
            val exchangeRateScreenUIModel =
                (oldSTATE as ExchangeRateScreenState.Content).exchangeRateScreenUIModel
            exchangeRateScreenUIModel.from?.let { from ->
                exchangeRateScreenUIModel.to?.let { to ->
                    loadExchangeRate(from, to, intent.amount)
                }
            }
            oldSTATE
        }
        is ExchangeRateScreenIntent.ShowWarningToast -> {
            viewModelScope.launch { toastManager.showShortToast(intent.text) }
            oldSTATE
        }
    }

    private fun loadExchangeRate(from: String, to: String, amount: Double) = viewModelScope.launch {
        when (val exchangeRateResult = exchangeRateRepository.getExchangeRate(from, to, amount)) {
            is ResultWrapper.Fail -> interact(ExchangeRateScreenIntent.ShowError(exchangeRateResult.errorMessage))
            is ResultWrapper.Success -> interact(
                ExchangeRateScreenIntent.LoadedExchangeRate(
                    exchangeRateResult.value
                )
            )
        }
    }

    private fun loadCurrencies() = viewModelScope.launch {
        when (val currenciesResult = currencyRepository.getCurrencies()) {
            is ResultWrapper.Fail -> interact(ExchangeRateScreenIntent.ShowError(currenciesResult.errorMessage))
            is ResultWrapper.Success -> interact(
                ExchangeRateScreenIntent.LoadedCurrencies(
                    currenciesResult.value
                )
            )
        }
    }

    private companion object {
        const val DEBOUNCE_TIME = 500L
    }
}
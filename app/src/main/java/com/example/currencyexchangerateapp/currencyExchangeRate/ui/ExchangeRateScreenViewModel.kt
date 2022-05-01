package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import androidx.lifecycle.viewModelScope
import com.example.currencyexchangerateapp.common.viewModel.BaseViewModel
import com.example.currencyexchangerateapp.currencyExchangeRate.data.CurrenciesRepository
import com.example.currencyexchangerateapp.currencyExchangeRate.data.ExchangeRateRepository
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.usecases.AmountTextParsingUseCase
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.usecases.AmountTextValidationUseCase
import com.example.currencyexchangerateapp.utils.ResultWrapper
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
class ExchangeRateScreenViewModel @Inject constructor(
    private val currencyRepository: CurrenciesRepository,
    private val exchangeRateRepository: ExchangeRateRepository,
    private val amountTextValidationUseCase: AmountTextValidationUseCase,
    private val amountTextParsingUseCase: AmountTextParsingUseCase
) : BaseViewModel<ExchangeRateScreenState, ExchangeRateScreenIntent>(ExchangeRateScreenState.Loading) {

    private val amountFlow = MutableSharedFlow<Double>(replay = 1)
    private var lastAmount: Double = AMOUNT_DEFAULT_VALUE.toDouble()

    init {
        amountFlow.debounce(DEBOUNCE_TIME)
            .onEach {
                interact(ExchangeRateScreenIntent.LoadExchangeRate(it))
                lastAmount = it
            }.launchIn(viewModelScope)
    }

    override fun reduce(
        intent: ExchangeRateScreenIntent,
        oldSTATE: ExchangeRateScreenState
    ): ExchangeRateScreenState = when (intent) {
        ExchangeRateScreenIntent.LoadCurrencies -> {
            loadCurrencies()
            ExchangeRateScreenState.Loading
        }
        is ExchangeRateScreenIntent.EnterAmount -> {
            val validatedText = handleEnteredAmount(intent.amount)
            (oldSTATE as? ExchangeRateScreenState.Content)?.copy(
                amount = validatedText
            ) ?: oldSTATE
        }
        is ExchangeRateScreenIntent.LoadedCurrencies -> {
            ExchangeRateScreenState.Content(
                intent.currencies,
                null,
                null,
                "",
                AMOUNT_DEFAULT_VALUE
            )
        }
        is ExchangeRateScreenIntent.LoadedExchangeRate -> {
            (oldSTATE as? ExchangeRateScreenState.Content)?.copy(
                exchangeRate = intent.exchangeRate.amount
            ) ?: oldSTATE
        }
        is ExchangeRateScreenIntent.SelectCurrencyFrom -> {
            (oldSTATE as? ExchangeRateScreenState.Content)?.copy(
                from = intent.selectedCurrency
            )?.apply {
                if (from != null && to != null) {
                    interact(ExchangeRateScreenIntent.EnterAmount(amount))
                }
            } ?: oldSTATE
        }
        is ExchangeRateScreenIntent.SelectCurrencyTo -> {
            (oldSTATE as? ExchangeRateScreenState.Content)?.copy(
                to = intent.selectedCurrency
            )?.apply {
                if (from != null && to != null) {
                    interact(ExchangeRateScreenIntent.EnterAmount(amount))
                }
            } ?: oldSTATE
        }
        is ExchangeRateScreenIntent.ShowError -> {
            ExchangeRateScreenState.Error(intent.message)
        }
        is ExchangeRateScreenIntent.LoadExchangeRate -> {
            val content = (oldSTATE as? ExchangeRateScreenState.Content)
            content?.from?.let { from ->
                content.to?.let { to ->
                    loadExchangeRate(from, to, intent.amount)
                }
            }
            oldSTATE
        }
    }

    private fun loadExchangeRate(from: String, to: String, amount: Double) = viewModelScope.launch {
        when (val exchangeRateResult = exchangeRateRepository.getExchangeRate(from, to, amount)) {
            is ResultWrapper.Fail -> interact(
                ExchangeRateScreenIntent.ShowError(exchangeRateResult.errorMessage)
            )
            is ResultWrapper.Success -> interact(
                ExchangeRateScreenIntent.LoadedExchangeRate(exchangeRateResult.value)
            )
        }
    }

    private fun loadCurrencies() = viewModelScope.launch {
        when (val currenciesResult = currencyRepository.getCurrencies()) {
            is ResultWrapper.Fail -> interact(
                ExchangeRateScreenIntent.ShowError(currenciesResult.errorMessage)
            )
            is ResultWrapper.Success -> interact(
                ExchangeRateScreenIntent.LoadedCurrencies(currenciesResult.value)
            )
        }
    }

    private fun handleEnteredAmount(enteredAmount: String): String {
        val validatedText = amountTextValidationUseCase(enteredAmount)
        amountTextParsingUseCase(validatedText)?.let {
            viewModelScope.launch {
                amountFlow.tryEmit(it)
            }
        }
        if (validatedText.isEmpty()) {
            viewModelScope.launch {
                amountFlow.tryEmit(lastAmount)
            }
        }
        return validatedText
    }

    private companion object {
        const val DEBOUNCE_TIME = 500L
        const val AMOUNT_DEFAULT_VALUE = "1.0"
    }
}

package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchangerateapp.R
import com.example.currencyexchangerateapp.currencyExchangeRate.domain.models.Currency

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun ExchangeRateScreenContent(
    content: ExchangeRateScreenState.Content,
    selectCurrencyFrom: (String) -> Unit,
    selectCurrencyTo: (String) -> Unit,
    onAmountEntered: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small),
                vertical = dimensionResource(R.dimen.padding_large)
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.exchange_rate_screen_title),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.horizontal_spacer_height_big)))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownCurrenciesMenu(
                    modifier = Modifier
                        .weight(1f)
                        .height(dimensionResource(R.dimen.currency_dropdown_menu_height)),
                    currencies = content.currencies.filter {
                        it.code != content.to
                    },
                    selected = content.from,
                    onSelected = selectCurrencyFrom
                )

                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.vertical_spacer_width)))

                DropdownCurrenciesMenu(
                    modifier = Modifier
                        .weight(1f)
                        .height(dimensionResource(R.dimen.currency_dropdown_menu_height)),
                    currencies = content.currencies.filter {
                        it.code != content.from
                    },
                    selected = content.to,
                    onSelected = selectCurrencyTo
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.horizontal_spacer_height_big)))

            if (content.isSelectedCurrencies) {
                val currencyFrom = content.from!!
                val currencyTo = content.to!!
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val focusManager = LocalFocusManager.current
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = content.amount,
                        onValueChange = onAmountEntered,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        )
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.vertical_spacer_width)))

                    Text(
                        modifier = Modifier.weight(1f),
                        text = currencyFrom,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h2
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.horizontal_spacer_height_small)))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = content.exchangeRate,
                        onValueChange = {},
                        singleLine = true,
                        readOnly = true,
                        enabled = false
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.vertical_spacer_width)))

                    Text(
                        text = currencyTo, modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DropdownCurrenciesMenu(
    modifier: Modifier = Modifier,
    currencies: List<Currency>,
    selected: String?,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        val dropdownMenuFieldValue = currencies.find { it.code == selected }?.name
            ?: stringResource(R.string.currency_dropdown_menu_hint)

        TextField(
            readOnly = true,
            value = dropdownMenuFieldValue,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            singleLine = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant)
        ) {
            currencies.forEach {
                DropdownMenuItem(
                    onClick = {
                        onSelected(it.code)
                        expanded = false
                    }
                ) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

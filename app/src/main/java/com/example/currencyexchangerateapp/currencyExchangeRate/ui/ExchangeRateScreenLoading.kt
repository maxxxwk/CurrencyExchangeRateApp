package com.example.currencyexchangerateapp.currencyExchangeRate.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchangerateapp.R


@Composable
fun ExchangeRateScreenLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .width(dimensionResource(R.dimen.loading_progress_indicator_width))
                .height(dimensionResource(R.dimen.loading_progress_indicator_height)),
            color = MaterialTheme.colors.secondary
        )
        AnimatedLoadingText()
    }
}

@Composable
fun AnimatedLoadingText() {
    val value by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 6000
                1f at 1000
            },
            repeatMode = RepeatMode.Restart
        )
    )
    val text = buildString {
        append("Loading")
        repeat(value.toInt()) {
            append(".")
        }
    }
    Text(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_big)),
        text = text,
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Start
    )
}

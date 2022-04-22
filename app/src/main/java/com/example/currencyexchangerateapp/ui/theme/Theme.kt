package com.example.currencyexchangerateapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Typography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Color.Black
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.Black
    ),
    subtitle1  = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        color = Color.Black
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Black
    )
)

private val ColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.LightGray,
    secondary = Color.Black,
    secondaryVariant = Color.DarkGray,
    background = Color.White,
    error = Color.Red
)


@Composable
fun CurrencyExchangeRateAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

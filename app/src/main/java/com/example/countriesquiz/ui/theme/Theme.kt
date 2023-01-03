package com.example.countriesquiz.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColors(
  primary = LightBlue,
  primaryVariant = PurpleGrey80,
  secondary = Pink80,
  background = LightGray,
)

@Composable
fun CountriesQuizTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colors = LightColorScheme,
    typography = Typography,
    content = content
  )
}
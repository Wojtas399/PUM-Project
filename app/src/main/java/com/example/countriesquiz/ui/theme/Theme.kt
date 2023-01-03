package com.example.countriesquiz.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColors(
  primary = Primary,
  background = Background,
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
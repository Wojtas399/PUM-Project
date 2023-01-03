package com.example.countriesquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import com.example.countriesquiz.ui.theme.CountriesQuizTheme
import com.example.countriesquiz.features.home.Home
import com.example.countriesquiz.ui.theme.Primary
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CountriesQuizTheme {
        val systemUiController = rememberSystemUiController()
        SideEffect {
          systemUiController.setStatusBarColor(
            color = Primary,
            darkIcons = false
          )
        }

        Home()
      }
    }
  }
}
package com.example.countriesquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.countriesquiz.ui.theme.CountriesQuizTheme
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CountriesQuizTheme {
        MainScreenView()
      }
    }
  }
}

@Composable
fun MainScreenView() {
  val navController = rememberNavController()
  Scaffold(
    bottomBar = { BottomNavigation(navController = navController) }
  ) { padding ->
    Column(
      modifier = Modifier.padding(padding)
    ) {
      NavigationGraph(navController = navController)
    }
  }
}
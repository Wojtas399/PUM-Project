package com.example.countriesquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countriesquiz.ui.theme.CountriesQuizTheme
import com.example.countriesquiz.ui.screens.Home
import com.example.countriesquiz.ui.nav.NavItem
import com.example.countriesquiz.ui.screens.Quiz
import com.example.countriesquiz.ui.screens.QuizType
import com.example.countriesquiz.ui.theme.Primary
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        MainApp()
      }
    }
  }
}

@Composable
private fun MainApp() {
  val globalNavController = rememberNavController()

  NavHost(
    navController = globalNavController,
    startDestination = NavItem.Home.screen_route
  ) {
    composable(NavItem.Home.screen_route) {
      Home(globalNavController = globalNavController)
    }
    composable("${NavItem.Quiz.screen_route}/{quizType}") { backStackEntry ->
      Quiz(
        quizType = backStackEntry.arguments?.getString("quizType")
          ?.toQuizType(),
        globalNavController = globalNavController,
      )
    }
  }
}

private fun String.toQuizType(): QuizType? {
  return when (this) {
    "Capitals" -> QuizType.Capitals
    "Flags" -> QuizType.Flags
    "Populations" -> QuizType.Populations
    else -> null
  }
}

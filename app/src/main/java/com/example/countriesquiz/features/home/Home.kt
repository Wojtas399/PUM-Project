package com.example.countriesquiz.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countriesquiz.features.home.bottomNav.BottomNavItem
import com.example.countriesquiz.features.home.bottomNav.BottomNavigation
import com.example.countriesquiz.features.library.LibraryScreen
import com.example.countriesquiz.features.progress.ProgressScreen
import com.example.countriesquiz.features.quiz_selection.QuizSelectionScreen

@Composable
fun Home(
  homeViewModel: HomeViewModel = viewModel(),
  globalNavController: NavHostController,
) {
  val homeState by homeViewModel.state.collectAsState()
  val bottomNavController = rememberNavController()
  bottomNavController.addOnDestinationChangedListener { _, destination, _ ->
    destination.route?.let {
      homeViewModel.onScreenChanged(it)
    }
  }

  Scaffold(
    bottomBar = {
      BottomNavigation(navController = bottomNavController)
    },
    topBar = {
      TopAppBar(
        title = { Text(homeState.screenTitle ?: "") },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.Black,
      )
    },
  ) { padding ->
    Column(
      modifier = Modifier.padding(padding)
    ) {
      HomeNavGraph(
        globalNavController = globalNavController,
        bottomNavController = bottomNavController
      )
    }
  }
}

@Composable
private fun HomeNavGraph(
  globalNavController: NavHostController,
  bottomNavController: NavHostController,
) {
  NavHost(
    navController = bottomNavController,
    startDestination = BottomNavItem.QuizSelection.screen_route
  ) {
    composable(BottomNavItem.QuizSelection.screen_route) {
      QuizSelectionScreen(globalNavController)
    }
    composable(BottomNavItem.Library.screen_route) {
      LibraryScreen()
    }
    composable(BottomNavItem.Progress.screen_route) {
      ProgressScreen()
    }
  }
}

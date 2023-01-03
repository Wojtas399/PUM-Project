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
import androidx.navigation.compose.rememberNavController

@Composable
fun Home(
  homeViewModel: HomeViewModel = viewModel(),
) {
  val homeState by homeViewModel.state.collectAsState()
  val navController = rememberNavController()
  navController.addOnDestinationChangedListener { _, destination, _ ->
    destination.route?.let {
      homeViewModel.onScreenChanged(it)
    }
  }

  Scaffold(
    bottomBar = { BottomNavigation(navController = navController) },
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
      NavigationGraph(navController = navController)
    }
  }
}
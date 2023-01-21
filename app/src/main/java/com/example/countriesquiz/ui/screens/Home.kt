package com.example.countriesquiz.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.countriesquiz.ui.nav.BottomNavItem
import com.example.countriesquiz.view_models.HomeViewModel

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
        title = {
          Text(
            text = homeState.screenTitle ?: "",
            color = Color.White,
          )
        },
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
fun BottomNavigation(navController: NavController) {
  val items = listOf(
    BottomNavItem.QuizSelection,
    BottomNavItem.Library,
    BottomNavItem.Progress,
  )
  BottomNavigation {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    items.forEach { item ->
      BottomNavigationItem(
        icon = {
          Icon(
            painterResource(id = item.icon),
            contentDescription = item.title
          )
        },
        label = {
          Text(
            text = item.title,
            fontSize = 9.sp
          )
        },
        selectedContentColor = Color.Black,
        unselectedContentColor = Color.Black.copy(0.4f),
        alwaysShowLabel = true,
        selected = currentRoute == item.screen_route,
        onClick = {
          navController.navigate(item.screen_route) {
            navController.graph.startDestinationRoute?.let { screen_route ->
              popUpTo(screen_route) {
                saveState = true
              }
            }
            launchSingleTop = true
            restoreState = true
          }
        }
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

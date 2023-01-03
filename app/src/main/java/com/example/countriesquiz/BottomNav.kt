package com.example.countriesquiz

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.countriesquiz.features.library.LibraryScreen
import com.example.countriesquiz.features.quiz.QuizScreen

@Composable
fun BottomNavigation(navController: NavController) {
  val items = listOf(
    BottomNavItem.Quiz,
    BottomNavItem.Library,
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
fun NavigationGraph(navController: NavHostController) {
  NavHost(navController, startDestination = BottomNavItem.Quiz.screen_route) {
    composable(BottomNavItem.Quiz.screen_route) {
      QuizScreen()
    }
    composable(BottomNavItem.Library.screen_route) {
      LibraryScreen()
    }
  }
}
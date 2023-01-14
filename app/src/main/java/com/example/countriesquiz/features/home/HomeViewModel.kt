package com.example.countriesquiz.features.home

import androidx.lifecycle.ViewModel
import com.example.countriesquiz.features.home.bottomNav.BottomNavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeState(
  val screenTitle: String? = null
)

class HomeViewModel : ViewModel() {
  private val _state = MutableStateFlow(HomeState())

  val state: StateFlow<HomeState> = _state.asStateFlow()

  fun onScreenChanged(route: String) {
    _state.update { currentState ->
      currentState.copy(screenTitle = getRouteTitle(route))
    }
  }

  private fun getRouteTitle(route: String) = when (route) {
    BottomNavItem.QuizSelection.screen_route -> BottomNavItem.QuizSelection.title
    BottomNavItem.Library.screen_route -> BottomNavItem.Library.title
    BottomNavItem.Progress.screen_route -> BottomNavItem.Progress.title
    else -> ""
  }
}
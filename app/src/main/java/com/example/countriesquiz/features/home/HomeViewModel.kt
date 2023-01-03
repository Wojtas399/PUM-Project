package com.example.countriesquiz.features.home

import androidx.lifecycle.ViewModel
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
    BottomNavItem.Quiz.screen_route -> BottomNavItem.Quiz.title
    BottomNavItem.Library.screen_route -> BottomNavItem.Library.title
    else -> ""
  }
}
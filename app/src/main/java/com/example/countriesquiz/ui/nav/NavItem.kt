package com.example.countriesquiz.ui.nav

sealed class NavItem(
  val screen_route: String,
) {
  object Home: NavItem("home")
  object Quiz: NavItem("quiz")
}
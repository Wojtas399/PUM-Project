package com.example.countriesquiz.features.home

sealed class NavItem(
  val screen_route: String,
) {
  object Home: NavItem("home")
  object Quiz: NavItem("quiz")
  object Summary: NavItem("summary")
}
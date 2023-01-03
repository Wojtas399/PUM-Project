package com.example.countriesquiz.features.home

import com.example.countriesquiz.R

sealed class BottomNavItem(
  var title: String,
  var icon: Int,
  var screen_route: String
) {
  object Quiz : BottomNavItem("Quiz", R.drawable.ic_quiz_24, "quiz")
  object Library : BottomNavItem("Library", R.drawable.ic_library_24, "library")
}
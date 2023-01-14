package com.example.countriesquiz.features.home.bottomNav

import com.example.countriesquiz.R

sealed class BottomNavItem(
  var title: String,
  var icon: Int,
  var screen_route: String
) {
  object QuizSelection : BottomNavItem("Quiz Selection", R.drawable.ic_quiz_24, "quiz_selection")
  object Library : BottomNavItem("Countries Library", R.drawable.ic_library_24, "library")
  object Progress: BottomNavItem("Progress", R.drawable.ic_show_chart_24, "progress")
}
package com.example.countriesquiz.features.library

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.countriesquiz.domain.entities.Country

@Composable
fun LibraryScreen(
  libraryViewModel: LibraryViewModel = hiltViewModel(),
) {
  val libraryState by libraryViewModel.state.collectAsState()
  val countries: List<Country>? = libraryState.countries

  if (countries != null) {
    LazyColumn {
      items(countries) { country ->
        Text(text = country.name)
      }
    }
  } else {
    Text(text = "There is no countries...")
  }
}
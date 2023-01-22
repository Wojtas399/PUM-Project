package com.example.countriesquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.countriesquiz.entities.Country
import com.example.countriesquiz.view_models.LibraryViewModel

@Composable
fun LibraryScreen(
  libraryViewModel: LibraryViewModel = hiltViewModel(),
) {
  val libraryState by libraryViewModel.state.collectAsState()
  val countries: List<Country>? = libraryState.countries

  if (countries != null) {
    LazyColumn {
      items(countries) { LibraryItem(country = it) }
    }
  } else {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    ) {
      Text(text = "Loading countries...")
    }
  }
}

@Composable
fun LibraryItem(
  country: Country
) {
  Row(
    modifier = Modifier.padding(all = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = country.flag,
      fontSize = 46.sp
    )
    Box(
      modifier = Modifier.width(16.dp)
    )
    Column {
      Text(
        text = country.name,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
      )
      Text(
        text = country.capital,
        color = Color.DarkGray
      )
    }
  }
}

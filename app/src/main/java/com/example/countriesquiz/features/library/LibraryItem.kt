package com.example.countriesquiz.features.library

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countriesquiz.domain.entities.Country

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
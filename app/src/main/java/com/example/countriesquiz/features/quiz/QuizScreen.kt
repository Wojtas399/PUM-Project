package com.example.countriesquiz.features.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

enum class QuizType {
  Capitals,
  Flags,
  Populations,
}

@Composable
fun QuizScreen(
  quizType: QuizType?,
  globalNavController: NavHostController,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(text = "$quizType Quiz")
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
          IconButton(
            onClick = { globalNavController.navigateUp() }
          ) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        },
      )
    },
  ) { padding ->
    Column(
      modifier = Modifier.padding(padding)
    ) {
      Text(
        text = "Quiz Screen $quizType",
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.align(Alignment.CenterHorizontally),
        textAlign = TextAlign.Center,
        fontSize = 20.sp
      )
    }
  }
}
package com.example.countriesquiz.features.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
  quizViewModel: QuizViewModel = hiltViewModel(),
) {
  val quizState by quizViewModel.state.collectAsState()
  val question: Question? = quizState.question
  
  LaunchedEffect(key1 = quizType) {
    quizViewModel.initialize(quizType)
  }

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
      modifier = Modifier.padding(padding),
    ) {
      if (question != null) {
        Text(text = question.question)
      } else {
        Text(text = "There is no drawn countries")
      }
    }
  }
}

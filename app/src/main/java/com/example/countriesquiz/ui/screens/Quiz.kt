package com.example.countriesquiz.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.countriesquiz.view_models.QuizViewModel

@Composable
fun Quiz(
  quizType: QuizType?,
  globalNavController: NavHostController,
  quizViewModel: QuizViewModel = hiltViewModel(),
) {
  val quizState by quizViewModel.state.collectAsState()
  val isQuizFinished: Boolean = quizState.isQuizFinished

  LaunchedEffect(key1 = quizType) {
    quizViewModel.initialize(quizType)
  }

  Scaffold(
    topBar = {
      TopAppBar(
        quizType = quizType,
        globalNavController = globalNavController
      )
    },
  ) { padding ->
    Box(
      modifier = Modifier.fillMaxSize().padding(padding)
    ) {
      if (isQuizFinished) {
        SummaryScreen(
          quizType = quizType,
          points = 6,
          questions = quizState.questions,
          globalNavController = globalNavController,
        )
      } else {
        QuizScreen(
          quizType = quizType,
          quizState = quizState,
          onNextQuestionButtonPressed = {
            quizViewModel.nextQuestion()
          },
        )
      }
    }
  }
}

@Composable
private fun TopAppBar(
  quizType: QuizType?,
  globalNavController: NavHostController,
) {
  androidx.compose.material.TopAppBar(
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
}

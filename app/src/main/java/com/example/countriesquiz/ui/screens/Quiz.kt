package com.example.countriesquiz.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.countriesquiz.features.quiz.QuizScreen
import com.example.countriesquiz.features.quiz.QuizType
import com.example.countriesquiz.features.summary.SummaryScreen
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

  if (isQuizFinished) {
    SummaryScreen(
      points = quizState.points,
      questions = quizState.questions
    )
  } else {
    QuizScreen(
      quizState = quizState,
      onNextQuestionButtonPressed = {
        quizViewModel.nextQuestion()
      },
      globalNavController = globalNavController,
    )
  }
}
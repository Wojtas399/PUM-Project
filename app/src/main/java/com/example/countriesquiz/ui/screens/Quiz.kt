package com.example.countriesquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

  LaunchedEffect(key1 = quizType) {
    quizViewModel.initialize(quizType)
  }

  if (!quizState.areDataLoaded) {
    LoadingDialog()
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
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
    ) {
      if (quizState.isQuizFinished) {
        SummaryScreen(
          quizType = quizType,
          points = quizState.points,
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
}

@Composable
private fun LoadingDialog() {
  Dialog(
    onDismissRequest = {}
  ) {
    Column(
      modifier = Modifier
        .size(100.dp)
        .background(
          color = Color.White,
          shape = RoundedCornerShape(4.dp)
        ),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      CircularProgressIndicator()
    }
  }
}

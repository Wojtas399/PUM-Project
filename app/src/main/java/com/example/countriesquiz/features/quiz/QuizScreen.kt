package com.example.countriesquiz.features.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        quizType = quizType,
        globalNavController = globalNavController,
      )
    },
  ) { padding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
    ) {
      Column(
        modifier = Modifier
          .fillMaxHeight()
          .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        QuestionText(question)
        Spacer(modifier = Modifier.height(32.dp))
        Answers(
          quizType = quizType,
          answer1 = quizState.answer1,
          answer2 = quizState.answer2,
          answer3 = quizState.answer3,
          answer4 = quizState.answer4,
        )
      }
      QuizStats(
        questionNumber = quizState.questionNumber,
        points = quizState.points
      )
      NextQuestionButton(
        enabled = quizState.questionStatus == QuestionStatus.Checked,
        onClick = {
          quizViewModel.nextQuestion()
        }
      )
    }
  }
}

@Composable
fun TopAppBar(
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
fun QuestionText(
  question: Question?
) {
  Text(
    text = question?.question ?: "",
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
  )
}

@Composable
fun NextQuestionButton(
  enabled: Boolean,
  onClick: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Button(
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
      enabled = enabled,
      onClick = onClick
    ) {
      Text(text = "Next question")
    }
  }
}

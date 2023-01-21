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
import androidx.navigation.NavHostController
import com.example.countriesquiz.view_models.Question
import com.example.countriesquiz.view_models.QuestionStatus
import com.example.countriesquiz.view_models.QuizState

enum class QuizType {
  Capitals,
  Flags,
  Populations,
}

@Composable
fun QuizScreen(
  quizState: QuizState,
  onNextQuestionButtonPressed: () -> Unit,
  globalNavController: NavHostController,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        quizType = quizState.quizType,
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
        QuestionText(quizState.currentQuestion)
        Spacer(modifier = Modifier.height(32.dp))
        Answers(
          quizType = quizState.quizType,
          answer1 = quizState.currentQuestion?.answer1,
          answer2 = quizState.currentQuestion?.answer2,
          answer3 = quizState.currentQuestion?.answer3,
          answer4 = quizState.currentQuestion?.answer4,
        )
      }
      QuizStats(
        questionNumber = quizState.currentQuestionIndex + 1,
        points = quizState.points
      )
      BottomButton(
        currentQuestionIndex = quizState.currentQuestionIndex,
        enabled = quizState.currentQuestionStatus == QuestionStatus.Checked,
        onClick = onNextQuestionButtonPressed,
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
fun BottomButton(
  currentQuestionIndex: Int,
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
      Text(
        text = if (currentQuestionIndex == 9) {
          "Finish"
        } else {
          "Next question"
        }
      )
    }
  }
}

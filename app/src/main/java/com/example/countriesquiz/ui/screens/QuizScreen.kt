package com.example.countriesquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countriesquiz.view_models.Answer
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
  quizType: QuizType?,
  quizState: QuizState,
  onNextQuestionButtonPressed: () -> Unit,
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
      quizType = quizType,
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

@Composable
fun QuizStats(
  questionNumber: Int,
  points: Int,
) {
  Row(
    modifier = Modifier
      .fillMaxSize()
      .padding(48.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = "Question", fontSize = 18.sp)
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = "$questionNumber/10", fontSize = 18.sp)
    }
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = "Correct answers", fontSize = 18.sp)
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = "$points", fontSize = 18.sp)
    }
  }
}

@Composable
fun QuestionText(
  question: Question?
) {
  Text(
    text = question?.value ?: "",
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
  )
}

@Composable
fun Answers(
  quizType: QuizType?,
  answer1: Answer?,
  answer2: Answer?,
  answer3: Answer?,
  answer4: Answer?,
) {
  Column {
    Row {
      AnswerButton(quizType, answer1)
      Spacer(modifier = Modifier.width(40.dp))
      AnswerButton(quizType, answer2)
    }
    Spacer(modifier = Modifier.height(24.dp))
    Row {
      AnswerButton(quizType, answer3)
      Spacer(modifier = Modifier.width(40.dp))
      AnswerButton(quizType, answer4)
    }
  }
}

@Composable
private fun AnswerButton(
  quizType: QuizType?,
  answer: Answer?
) {
  answer?.onClick?.let {
    Button(
      modifier = Modifier
        .width(145.dp)
        .height(60.dp),
      onClick = it,
      colors = ButtonDefaults.buttonColors(backgroundColor = answer.color)
    ) {
      Text(
        text = answer.value ?: "",
        textAlign = TextAlign.Center,
        fontSize = if (quizType == QuizType.Flags) 28.sp else 18.sp,
      )
    }
  }
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

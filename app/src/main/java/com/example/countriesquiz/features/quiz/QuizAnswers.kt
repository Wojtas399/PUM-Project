package com.example.countriesquiz.features.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        text = answer.answer ?: "",
        textAlign = TextAlign.Center,
        fontSize = if (quizType == QuizType.Flags) 28.sp else 18.sp,
      )
    }
  }
}
package com.example.countriesquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.countriesquiz.view_models.Answer
import com.example.countriesquiz.view_models.Question

@Composable
fun SummaryScreen(
  quizType: QuizType?,
  points: Int,
  questions: List<Question>,
  globalNavController: NavHostController,
) {
  Box {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      item {
        Spacer(modifier = Modifier.height(24.dp))
        Header()
        Spacer(modifier = Modifier.height(32.dp))
        Points(points = points)
        Spacer(modifier = Modifier.height(32.dp))
      }
      items(questions) {
        QuestionItem(
          question = it,
          quizType = quizType,
        )
      }
      item {
        Spacer(modifier = Modifier.height(100.dp))
      }
    }
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 24.dp),
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Button(
        modifier = Modifier
          .width(200.dp)
          .height(60.dp),
        onClick = { globalNavController.navigateUp() }
      ) {
        Text(text = "Home")
      }
    }
  }
}

@Composable
private fun Header() {
  Text(
    text = "🎉 CONGRATS! 🎉",
    fontSize = 36.sp,
    fontWeight = FontWeight.Bold,
  )
}

@Composable
private fun Points(points: Int) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Your scored points:",
      fontSize = 24.sp,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "$points/10",
      fontSize = 32.sp,
      fontWeight = FontWeight.Bold,
    )
  }
}

@Composable
private fun QuestionItem(
  question: Question,
  quizType: QuizType?,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 24.dp, start = 24.dp, end = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = question.value,
      fontSize = 20.sp,
      textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Column {
      Row {
        AnswerItem(question.answer1, quizType)
        Spacer(modifier = Modifier.width(8.dp))
        AnswerItem(question.answer2, quizType)
      }
      Spacer(modifier = Modifier.height(4.dp))
      Row {
        AnswerItem(question.answer3, quizType)
        Spacer(modifier = Modifier.width(8.dp))
        AnswerItem(question.answer4, quizType)
      }
    }
  }
}

@Composable
private fun AnswerItem(
  answer: Answer,
  quizType: QuizType?,
) {
  Text(
    modifier = Modifier
      .width(145.dp)
      .height(60.dp)
      .background(color = answer.color, shape = RoundedCornerShape(8.dp))
      .wrapContentSize(Alignment.Center),
    text = answer.value ?: "",
    fontSize = if (quizType == QuizType.Flags) 30.sp else 18.sp,
  )
}

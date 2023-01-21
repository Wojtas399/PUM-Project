package com.example.countriesquiz.features.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
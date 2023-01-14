package com.example.countriesquiz.features.quiz_selection

import androidx.compose.foundation.layout.*
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
import com.example.countriesquiz.features.home.NavItem
import com.example.countriesquiz.features.quiz.QuizType

@Composable
fun QuizSelectionScreen(
  globalNavController: NavHostController,
) {
  fun navigateToQuizScreen(quizType: QuizType) {
    globalNavController.navigate(
      "${NavItem.Quiz.screen_route}/$quizType"
    )
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.Center)
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = "Wybierz quiz",
      fontWeight = FontWeight.Bold,
      fontSize = 32.sp,
      textAlign = TextAlign.Center,
    )
    FreeSpace()
    CustomButton(
      label = "Stolice",
      onClick = {
        navigateToQuizScreen(QuizType.Capitals)
      },
    )
    FreeSpace()
    CustomButton(
      label = "Flagi",
      onClick = {
        navigateToQuizScreen(QuizType.Flags)
      },
    )
    FreeSpace()
    CustomButton(
      label = "Populacje",
      onClick = {
        navigateToQuizScreen(QuizType.Populations)
      }
    )
  }
}

@Composable
private fun FreeSpace() {
  Box(
    modifier = Modifier.height(32.dp)
  )
}

@Composable
private fun CustomButton(
  label: String,
  onClick: () -> Unit,
) {
  Button(
    onClick = onClick,
    modifier = Modifier.size(width = 240.dp, height = 56.dp)
  ) {
    Text(
      text = label,
      fontSize = 18.sp,
    )
  }
}

package com.example.countriesquiz.features.quiz

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.countriesquiz.domain.entities.Country
import com.example.countriesquiz.domain.useCases.GetRandomCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

enum class QuestionStatus {
  Checked,
  Unchecked,
}

data class QuizState(
  val points: Int = 0,
  val questionNumber: Int = 0,
  val questionStatus: QuestionStatus = QuestionStatus.Unchecked,
  val question: Question? = null,
  val answer1: Answer? = null,
  val answer2: Answer? = null,
  val answer3: Answer? = null,
  val answer4: Answer? = null,
)

@HiltViewModel
class QuizViewModel @Inject constructor(
  private val getRandomCountriesUseCase: GetRandomCountriesUseCase,
) : ViewModel() {
  private val _state = MutableStateFlow(QuizState())
  private var quizType: QuizType? = null
  private var drawnCountries: MutableList<Country> = mutableListOf()

  val state: StateFlow<QuizState> = _state.asStateFlow()

  fun initialize(quizType: QuizType?) {
    this.quizType = quizType
    runBlocking {
      drawnCountries = getRandomCountriesUseCase(amount = 10).toMutableList()
      setFirstQuestion()
    }
  }

  fun nextQuestion() {
    val questionNumber = _state.value.questionNumber + 1
    val question = getQuestionByIndex(questionNumber - 1)
    val answers = getIncorrectAnswers(question.answer)
    answers.add(createUncheckedAnswer(question.answer))
    val shuffledAnswers = answers.shuffled()
    _state.update { currentState ->
      currentState.copy(
        questionNumber = questionNumber,
        questionStatus = QuestionStatus.Unchecked,
        question = question,
        answer1 = shuffledAnswers[0],
        answer2 = shuffledAnswers[1],
        answer3 = shuffledAnswers[2],
        answer4 = shuffledAnswers[3],
      )
    }
  }

  private fun setFirstQuestion() {
    val firstQuestion: Question = getQuestionByIndex(0)
    val answers = getIncorrectAnswers(correctAnswer = firstQuestion.answer)
    answers.add(createUncheckedAnswer(firstQuestion.answer))
    val shuffledAnswers = answers.shuffled()
    _state.update { currentState ->
      currentState.copy(
        questionNumber = 1,
        question = firstQuestion,
        answer1 = shuffledAnswers[0],
        answer2 = shuffledAnswers[1],
        answer3 = shuffledAnswers[2],
        answer4 = shuffledAnswers[3],
      )
    }
  }

  private fun getQuestionByIndex(index: Int): Question {
    if (drawnCountries.isNotEmpty()) {
      val country: Country = drawnCountries[index]
      return when (quizType) {
        QuizType.Capitals -> Question(
          question = "What is the capital of ${country.name}?",
          answer = country.capital,
        )
        QuizType.Flags -> Question(
          question = "What is the flag of ${country.name}?",
          answer = country.flag
        )
        QuizType.Populations -> Question(
          question = "What is the population of ${country.name}?",
          answer = "${country.population}"
        )
        else -> throw Throwable("Unknown quiz type")
      }
    }
    throw Throwable("...")
  }

  private fun getIncorrectAnswers(correctAnswer: String): MutableList<Answer> {
    val countriesToDraw = mutableListOf<Country>()
    countriesToDraw.addAll(drawnCountries)
    val answersToDraw = countriesToDraw.map {
      val answer: String = when (quizType) {
        QuizType.Capitals -> it.capital
        QuizType.Flags -> it.flag
        QuizType.Populations -> "${it.population}"
        else -> throw Throwable("Unknown quiz type")
      }
      createUncheckedAnswer(answer)
    }.toMutableList()
    answersToDraw.removeIf { it.answer == correctAnswer }
    return answersToDraw.shuffled().take(3).toMutableList()
  }

  private fun createUncheckedAnswer(answer: String?): Answer {
    return Answer(
      answer = answer,
      onClick = {
        answer?.let { checkAnswer(it) }
      },
      color = Color.LightGray,
    )
  }

  private fun checkAnswer(selectedAnswer: String) {
    val isCorrectAnswer = selectedAnswer == _state.value.question?.answer
    val points = _state.value.points.let {
      if (isCorrectAnswer) it + 1 else it
    }
    _state.update { currentState ->
      currentState.copy(
        points = points,
        questionStatus = QuestionStatus.Checked,
        answer1 = makeAnswerChecked(_state.value.answer1),
        answer2 = makeAnswerChecked(_state.value.answer2),
        answer3 = makeAnswerChecked(_state.value.answer3),
        answer4 = makeAnswerChecked(_state.value.answer4),
      )
    }
  }

  private fun makeAnswerChecked(answer: Answer?): Answer {
    return Answer(
      answer = answer?.answer,
      onClick = {},
      color = if (answer?.answer == _state.value.question?.answer) {
        Color.Green
      } else {
        Color.Red
      }
    )
  }
}

data class Question(
  val question: String,
  val answer: String,
)

data class Answer(
  val answer: String?,
  val onClick: () -> Unit,
  val color: Color,
)

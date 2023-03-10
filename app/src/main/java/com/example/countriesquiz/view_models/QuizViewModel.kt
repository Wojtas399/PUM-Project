package com.example.countriesquiz.view_models

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesquiz.entities.Country
import com.example.countriesquiz.repositories.CountryRepository
import com.example.countriesquiz.ui.screens.QuizType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class QuestionStatus {
  Checked,
  Unchecked,
}

data class QuizState(
  val areDataLoaded: Boolean = false,
  val points: Int = 0,
  val currentQuestionIndex: Int = 0,
  val currentQuestionStatus: QuestionStatus = QuestionStatus.Unchecked,
  val currentQuestion: Question? = null,
  val questions: List<Question> = listOf(),
  val isQuizFinished: Boolean = false,
)

@HiltViewModel
class QuizViewModel @Inject constructor(
  private val countryRepository: CountryRepository,
) : ViewModel() {
  private val _state = MutableStateFlow(QuizState())
  private var quizType: QuizType? = null
  private var drawnCountries: MutableList<Country> = mutableListOf()

  val state: StateFlow<QuizState> = _state.asStateFlow()

  fun initialize(quizType: QuizType?) {
    this.quizType = quizType
    viewModelScope.launch {
      val allCountries = countryRepository.getAllCountries()
      drawnCountries = allCountries.shuffled().take(10).toMutableList()
      initializeQuestions()
    }
  }

  fun nextQuestion() {
    val nextQuestionIndex = _state.value.currentQuestionIndex + 1
    if (nextQuestionIndex < 10) {
      _state.update { currentState ->
        currentState.copy(
          currentQuestionIndex = nextQuestionIndex,
          currentQuestionStatus = QuestionStatus.Unchecked,
          currentQuestion = _state.value.questions[nextQuestionIndex]
        )
      }
    } else {
      _state.update { currentState ->
        currentState.copy(isQuizFinished = true)
      }
    }
  }

  private fun initializeQuestions() {
    val questions = mutableListOf<Question>()
    for (index in 0..9) {
      questions.add(
        createQuestion(index)
      )
    }
    _state.update { currentState ->
      currentState.copy(
        areDataLoaded = true,
        currentQuestionIndex = 0,
        questions = questions.toList(),
        currentQuestion = questions.first(),
      )
    }
  }

  private fun createQuestion(index: Int): Question {
    if (drawnCountries.isNotEmpty()) {
      val country: Country = drawnCountries[index]
      val question: String
      val correctAnswer: String
      when (quizType) {
        QuizType.Capitals -> {
          question = "What is the capital of ${country.name}?"
          correctAnswer = country.capital
        }
        QuizType.Flags -> {
          question = "What is the flag of ${country.name}?"
          correctAnswer = country.flag
        }
        QuizType.Populations -> {
          question = "What is the population of ${country.name}?"
          correctAnswer = "${country.population}"
        }
        else -> throw Throwable("Unknown quiz type")
      }
      val answers = getIncorrectAnswers(correctAnswer, quizType)
      answers.add(
        createUncheckedAnswer(correctAnswer)
      )
      val shuffledAnswers = answers.shuffled()
      return Question(
        value = question,
        correctAnswer = correctAnswer,
        answer1 = shuffledAnswers[0],
        answer2 = shuffledAnswers[1],
        answer3 = shuffledAnswers[2],
        answer4 = shuffledAnswers[3],
      )
    }
    throw Throwable("...")
  }

  private fun getIncorrectAnswers(
    correctAnswer: String,
    quizType: QuizType?,
  ): MutableList<Answer> {
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
    answersToDraw.removeIf { it.value == correctAnswer }
    return answersToDraw.shuffled().take(3).toMutableList()
  }

  private fun createUncheckedAnswer(answer: String?): Answer {
    return Answer(
      value = answer,
      onClick = {
        answer?.let { checkAnswer(it) }
      },
      color = Color.LightGray,
    )
  }

  private fun checkAnswer(selectedAnswer: String) {
    val questionIndex = _state.value.currentQuestionIndex
    val question = _state.value.questions[questionIndex]
    val isCorrectAnswer = selectedAnswer == question.correctAnswer
    val points = _state.value.points.let {
      if (isCorrectAnswer) it + 1 else it
    }
    question.answer1 = makeAnswerChecked(question.answer1)
    question.answer2 = makeAnswerChecked(question.answer2)
    question.answer3 = makeAnswerChecked(question.answer3)
    question.answer4 = makeAnswerChecked(question.answer4)
    val updatedQuestions = _state.value.questions.toMutableList()
    updatedQuestions[questionIndex] = question
    _state.update { currentState ->
      currentState.copy(
        points = points,
        currentQuestionStatus = QuestionStatus.Checked,
        questions = updatedQuestions
      )
    }
  }

  private fun makeAnswerChecked(answer: Answer?): Answer {
    val currentQuestionIndex = _state.value.currentQuestionIndex
    return Answer(
      value = answer?.value,
      onClick = {},
      color = if (answer?.value == _state.value.questions[currentQuestionIndex].correctAnswer) {
        Color.Green
      } else {
        Color.Red
      }
    )
  }
}

data class Question(
  val value: String,
  val correctAnswer: String?,
  var answer1: Answer,
  var answer2: Answer,
  var answer3: Answer,
  var answer4: Answer,
)

data class Answer(
  val value: String?,
  val onClick: () -> Unit,
  val color: Color,
)

package com.example.countriesquiz.features.quiz

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

data class QuizState(
  val questionNumber: Int = 0,
  val question: Question? = null,
  val answer1: String? = null,
  val answer2: String? = null,
  val answer3: String? = null,
  val answer4: String? = null,
  val correctAnswer: String? = null,
)

@HiltViewModel
class QuizViewModel @Inject constructor(
  private val getRandomCountriesUseCase: GetRandomCountriesUseCase,
) : ViewModel() {
  private val _state = MutableStateFlow(QuizState())
  private var quizType: QuizType? = null
  private var drawnCountries: List<Country> = listOf()

  val state: StateFlow<QuizState> = _state.asStateFlow()

  fun initialize(quizType: QuizType?) {
    this.quizType = quizType
    runBlocking {
      drawnCountries = getRandomCountriesUseCase(amount = 10)
      val questionNumber = 1
      val firstQuestion: Question = getQuestionByIndex(questionNumber - 1)
      _state.update { currentState ->
        currentState.copy(
          questionNumber = questionNumber,
          question = firstQuestion,
        )
      }
    }
  }

  private fun getQuestionByIndex(index: Int): Question {
    if (drawnCountries.isNotEmpty()) {
      val country: Country = drawnCountries[index]
      return when (quizType) {
        QuizType.Capitals -> getQuestionForCapital(country)
        QuizType.Flags -> getQuestionForFlag(country)
        QuizType.Populations -> getQuestionForPopulation(country)
        else -> throw Throwable("Unknown quiz type")
      }
    }
    throw Throwable("...")
  }

  private fun getQuestionForCapital(country: Country): Question {
    return Question(
      question = "What is the capital of ${country.name}",
      answer = country.capital,
    )
  }

  private fun getQuestionForFlag(country: Country): Question {
    return Question(
      question = "What is the flag of ${country.name}",
      answer = country.flag
    )
  }

  private fun getQuestionForPopulation(country: Country): Question {
    return Question(
      question = "What is the population of ${country.name}",
      answer = "${country.population}"
    )
  }
}

data class Question(
  val question: String,
  val answer: String,
)

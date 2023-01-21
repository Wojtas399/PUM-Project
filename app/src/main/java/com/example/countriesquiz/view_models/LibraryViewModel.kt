package com.example.countriesquiz.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesquiz.entities.Country
import com.example.countriesquiz.repositories.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LibraryState(
  val countries: List<Country>? = null
)

@HiltViewModel
class LibraryViewModel @Inject constructor(
  private val countryRepository: CountryRepository
) : ViewModel() {
  private val _state = MutableStateFlow(LibraryState())

  val state: StateFlow<LibraryState> = _state.asStateFlow()

  init {
    viewModelScope.launch {
      val countries: List<Country> = countryRepository.getAllCountries()
      _state.update { currentState ->
        currentState.copy(
          countries = countries.sortedBy { it.name }
        )
      }
    }
  }
}
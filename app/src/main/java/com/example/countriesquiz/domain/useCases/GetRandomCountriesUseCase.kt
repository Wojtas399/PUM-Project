package com.example.countriesquiz.domain.useCases

import com.example.countriesquiz.domain.entities.Country
import com.example.countriesquiz.domain.repositories.CountryRepository
import javax.inject.Inject

class GetRandomCountriesUseCase @Inject constructor(
  private val countryRepository: CountryRepository
) {
  suspend operator  fun invoke(
    amount: Int,
  ) : List<Country> {
    val allCountries: List<Country> = countryRepository.getAllCountries()
    return allCountries.asSequence().shuffled().take(amount).toList()
  }
}
package com.example.countriesquiz.domain.useCases

import com.example.countriesquiz.domain.entities.Country
import com.example.countriesquiz.domain.repositories.CountryRepository
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
  private val countryRepository: CountryRepository
) {
  suspend operator fun invoke() : List<Country> {
    return countryRepository.getAllCountries()
  }
}
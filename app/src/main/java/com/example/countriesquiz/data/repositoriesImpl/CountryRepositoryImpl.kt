package com.example.countriesquiz.data.repositoriesImpl

import com.example.countriesquiz.data.countryApi.CountryApi
import com.example.countriesquiz.data.countryApi.CountryApiModel
import com.example.countriesquiz.domain.entities.Country
import com.example.countriesquiz.domain.repositories.CountryRepository
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
  private val countryApi: CountryApi
) : CountryRepository {
  override suspend fun getAllCountries(): List<Country> {
    return countryApi.getAllCountries().map { mapCountryApiModelToCountry(it) }
  }

  private fun mapCountryApiModelToCountry(apiCountry: CountryApiModel) : Country {
    return Country(
      name = apiCountry.name.official,
      capital = apiCountry.capital?.first() ?: "unknown",
      flag = apiCountry.flag,
    )
  }
}
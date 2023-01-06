package com.example.countriesquiz.domain.repositories

import com.example.countriesquiz.domain.entities.Country

interface CountryRepository {
  suspend fun getAllCountries() : List<Country>
}
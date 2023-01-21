package com.example.countriesquiz.repositories

import com.example.countriesquiz.entities.Country

interface CountryRepository {
  suspend fun getAllCountries() : List<Country>
}
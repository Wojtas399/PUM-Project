package com.example.countriesquiz.data.countryApi

data class CountryApiModel(
  val name: ApiCountryName,
  val capital: ArrayList<String>? = null,
  val flag: String,
  val population: Int,
)

data class ApiCountryName(
  val official: String,
)

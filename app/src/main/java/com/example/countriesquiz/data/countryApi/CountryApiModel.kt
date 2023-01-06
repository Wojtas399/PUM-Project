package com.example.countriesquiz.data.countryApi

data class CountryApiModel(
  val name: ApiCountryName,
  val capital: ArrayList<String>? = null,
  val flag: String,
)

data class ApiCountryName(
  val official: String,
)

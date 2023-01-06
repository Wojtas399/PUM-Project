package com.example.countriesquiz.data.countryApi

import retrofit2.http.GET

interface CountryApi {
  @GET("all")
  suspend fun getAllCountries() : List<CountryApiModel>
}
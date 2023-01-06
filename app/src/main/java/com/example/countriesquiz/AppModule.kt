package com.example.countriesquiz

import com.example.countriesquiz.data.countryApi.CountryApi
import com.example.countriesquiz.data.countryApi.CountryApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Singleton
  @Provides
  fun provideCountryApi(
    countryClient: CountryApiClient,
  ) : CountryApi {
    return countryClient.buildApi(CountryApi::class.java)
  }
}
package com.example.countriesquiz.data.countryApi

import com.example.countriesquiz.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountryApiClient @Inject constructor() {
  private val baseUrl: String = "https://restcountries.com/v3.1/"

  fun <Api> buildApi(
    api: Class<Api>
  ): Api {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(getRetrofitClient())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(api)
  }

  private fun getRetrofitClient(): OkHttpClient = OkHttpClient.Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(30, TimeUnit.SECONDS)
    .also { client ->
      if (BuildConfig.DEBUG) {
        client.addInterceptor(
          HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
          }
        )
      }
    }.build()
}
package com.saulhervas.laligateamsapp.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        private const val BASE_URL_API1 = "https://v3.football.api-sports.io/"

        fun getRetrofit(): Retrofit {
            return Retrofit
                .Builder()
                .baseUrl(BASE_URL_API1)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient
                        .Builder()
                        .addInterceptor { chain ->
                            val newRequest = chain.request().newBuilder()
                                .addHeader("X-RapidAPI-Key", "98fe0d509274182bfb18ff7b5e8f6d15")
                                .addHeader("X-RapidAPI-Host", "v3.football.api-sports.io")
                                .build()
                            chain.proceed(newRequest)
                        }
                        .build()
                )
                .build()
        }

    }
}
package com.saulhervas.laligateamsapp.utils


import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/standings?league=140&season=2023")
    suspend fun getStandings(): Response<StandingsDataResponse>

}
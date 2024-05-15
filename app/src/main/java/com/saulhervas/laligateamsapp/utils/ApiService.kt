package com.saulhervas.laligateamsapp.utils


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/teams")
    suspend fun getTeams(@Query("search") teamName: String): Response<TeamsDataResponse>

    @GET("/teams")
    suspend fun getTeam(@Query("id") teamId: Int): Response<TeamsDataResponse>
}
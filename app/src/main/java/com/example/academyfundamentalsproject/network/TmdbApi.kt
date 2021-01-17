package com.example.academyfundamentalsproject.network

import com.example.academyfundamentalsproject.network.models.ConfigurationModelDto
import retrofit2.http.GET

interface TmdbApi {
    @GET("configuration")
    suspend fun getTmdbConfig(): ConfigurationModelDto
}
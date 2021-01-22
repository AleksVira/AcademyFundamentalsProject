package com.example.academyfundamentalsproject.network

import com.example.academyfundamentalsproject.network.models.ConfigurationDto
import com.example.academyfundamentalsproject.network.models.TmdbMovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("configuration")
    suspend fun getTmdbConfig(): ConfigurationDto

    @GET("movie/top_rated")
    suspend fun getTopRated(
//        @Query("language") defaultLanguage: String,
        @Query("page") page: Int
    ): List<TmdbMovieDto>
}
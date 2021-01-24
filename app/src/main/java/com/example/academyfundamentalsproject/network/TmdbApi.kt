package com.example.academyfundamentalsproject.network

import com.example.academyfundamentalsproject.network.models.ConfigurationDto
import com.example.academyfundamentalsproject.network.models.GenresResponse
import com.example.academyfundamentalsproject.network.models.MovieInfoResponse
import com.example.academyfundamentalsproject.network.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("configuration")
    suspend fun getTmdbConfig(): ConfigurationDto

    @GET("movie/top_rated")
    suspend fun getTopRated(
//        @Query("language") defaultLanguage: String,
        @Query("page") page: Int,
    ): MovieResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieInfoById(
        @Path("movie_id") movieId: Int
    ): MovieInfoResponse
}
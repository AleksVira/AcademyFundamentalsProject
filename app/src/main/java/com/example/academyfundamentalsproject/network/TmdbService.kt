package com.example.academyfundamentalsproject.network

import com.example.academyfundamentalsproject.network.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("configuration")
    suspend fun getTmdbConfig(): Resource<ConfigurationDto>

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

    @GET("movie/{movie_id}/credits")
    suspend fun getActorsById(
        @Path("movie_id") movieId: Int
    ): CreditsResponse

    @GET("movie/top_rated")
    fun getTopRatedPaged(
        @Query("page") page: Int,
    ): MovieResponse
}
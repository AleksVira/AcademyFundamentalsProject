package com.example.academyfundamentalsproject.view_models

import com.example.academyfundamentalsproject.network.TmdbApi
import com.example.academyfundamentalsproject.network.TmdbConverter
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData

interface TmdbRepository {
    suspend fun getTmdbConfig(): TmdbConfigData
    suspend fun getNetworkTopRated(): List<Movie>
}

class TmdbRepositoryImpl(
    private val tmdbApi: TmdbApi,
    private val converter: TmdbConverter,
) : TmdbRepository {

    override suspend fun getTmdbConfig(): TmdbConfigData {
        val networkResult = tmdbApi.getTmdbConfig()
        return converter.toTmdbConfig(networkResult)
    }

    override suspend fun getNetworkTopRated(): List<Movie> {
        val networkMovies = tmdbApi.getTopRated(1)
        return converter.toMoviesList(networkMovies)
    }

}

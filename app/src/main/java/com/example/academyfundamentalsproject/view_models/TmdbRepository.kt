package com.example.academyfundamentalsproject.view_models

import android.util.SparseArray
import com.example.academyfundamentalsproject.network.TmdbApi
import com.example.academyfundamentalsproject.network.TmdbConverter
import com.example.academyfundamentalsproject.network.models.GenresResponse
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData
import timber.log.Timber

interface TmdbRepository {
    suspend fun getTmdbConfig(): TmdbConfigData
    suspend fun getGenres(): SparseArray<String>
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
        val networkMoviesResponse = tmdbApi.getTopRated(1)
        Timber.d("MyTAG_TmdbRepositoryImpl_getNetworkTopRated(): $networkMoviesResponse")
        val networkMovies = networkMoviesResponse.results
        Timber.d("MyTAG_TmdbRepositoryImpl_getNetworkTopRated(): $networkMovies")
        return converter.toMoviesList(networkMovies)
    }

    override suspend fun getGenres(): SparseArray<String> {
        val networkResponse: GenresResponse = tmdbApi.getGenres()
        return converter.toGenresList(networkResponse)
    }

}

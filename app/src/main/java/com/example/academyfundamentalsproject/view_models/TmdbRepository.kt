package com.example.academyfundamentalsproject.view_models

import android.util.SparseArray
import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.network.TmdbApi
import com.example.academyfundamentalsproject.network.TmdbConverter
import com.example.academyfundamentalsproject.network.models.CreditsResponse
import com.example.academyfundamentalsproject.network.models.GenresResponse
import com.example.academyfundamentalsproject.network.models.MovieInfoResponse
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.SingleMovieInfo
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData
import timber.log.Timber

interface TmdbRepository {
    suspend fun getTmdbConfig(): TmdbConfigData
    suspend fun getGenres(): SparseArray<String>
    suspend fun getNetworkTopRated(): List<Movie>
    suspend fun getMovieInfo(movieId: Int): SingleMovieInfo
    suspend fun getActors(movieId: Int): List<Actor>?
}

class TmdbRepositoryImpl(
    private val tmdbApi: TmdbApi,
    private val converter: TmdbConverter,
) : TmdbRepository {

    var repoGenres = SparseArray<String>()

    override suspend fun getTmdbConfig(): TmdbConfigData {
        val networkResult = tmdbApi.getTmdbConfig()
        return converter.toTmdbConfig(networkResult)
    }

    override suspend fun getNetworkTopRated(): List<Movie> {
        val networkMoviesResponse = tmdbApi.getTopRated(1)
        Timber.d("MyTAG_TmdbRepositoryImpl_getNetworkTopRated(): $networkMoviesResponse")
        val networkMovies = networkMoviesResponse.results
        Timber.d("MyTAG_TmdbRepositoryImpl_getNetworkTopRated(): $networkMovies")
        return converter.toMoviesList(networkMovies, repoGenres)
    }

    override suspend fun getMovieInfo(movieId: Int): SingleMovieInfo {
        val networkMovieResponse: MovieInfoResponse = tmdbApi.getMovieInfoById(movieId)
        return converter.toSingleMovieInfo(networkMovieResponse)
    }

    override suspend fun getActors(movieId: Int): List<Actor>? {
        val networkActorsResponse: CreditsResponse = tmdbApi.getActorsById(movieId)
        return converter.toActorsList(networkActorsResponse)
    }

    override suspend fun getGenres(): SparseArray<String> {
        val networkResponse: GenresResponse = tmdbApi.getGenres()
        repoGenres = converter.toGenresList(networkResponse)
        return repoGenres
    }

}

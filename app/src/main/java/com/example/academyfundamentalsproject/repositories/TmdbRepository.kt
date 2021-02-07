package com.example.academyfundamentalsproject.repositories

import android.util.SparseArray
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.data.MoviePagingSource
import com.example.academyfundamentalsproject.network.Resource
import com.example.academyfundamentalsproject.network.TmdbConverter
import com.example.academyfundamentalsproject.network.TmdbService
import com.example.academyfundamentalsproject.network.models.CreditsResponse
import com.example.academyfundamentalsproject.network.models.GenresResponse
import com.example.academyfundamentalsproject.network.models.MovieInfoResponse
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.SingleMovieInfo
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {
    suspend fun getTmdbConfig(): Resource<TmdbConfigData>
    suspend fun getGenres(): SparseArray<String>
//    suspend fun getNetworkTopRated(): List<Movie>
    fun getPagedNetworkTopRated(): Flow<PagingData<Movie>>
    suspend fun getMovieInfo(movieId: Int): SingleMovieInfo
    suspend fun getActors(movieId: Int): List<Actor>?
    fun getAllGenres(): SparseArray<String>
}

class TmdbRepositoryImpl(
    private val tmdbService: TmdbService,
    private val converter: TmdbConverter,
) : TmdbRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    var repoGenres = SparseArray<String>()

    override suspend fun getTmdbConfig(): Resource<TmdbConfigData> {
        return when (val networkResult = tmdbService.getTmdbConfig()) {
            is Resource.Success -> {
//                delay(3000)
//                return Resource.Failure.HttpError(HttpException(404, "VERY BAD"))
                val convertResult = converter.toTmdbConfig(networkResult.value)
                Resource.Success.Value(convertResult)
            }
            is Resource.Failure<*> -> networkResult
            is Resource.Success.Empty -> Resource.Success.Empty
        }
    }

//    override suspend fun getNetworkTopRated(): List<Movie> {
//        val networkMoviesResponse = tmdbService.getTopRated(1)
//        val networkMovies = networkMoviesResponse.results
//        return converter.toMoviesList(networkMovies, repoGenres)
//    }

    override fun getPagedNetworkTopRated(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(tmdbService, converter, repoGenres) }
        ).flow
    }


    override suspend fun getMovieInfo(movieId: Int): SingleMovieInfo {
        val networkMovieResponse: MovieInfoResponse = tmdbService.getMovieInfoById(movieId)
        return converter.toSingleMovieInfo(networkMovieResponse)
    }

    override suspend fun getActors(movieId: Int): List<Actor>? {
        val networkActorsResponse: CreditsResponse = tmdbService.getActorsById(movieId)
        return converter.toActorsList(networkActorsResponse)
    }

    override fun getAllGenres(): SparseArray<String> {
        return repoGenres
    }

    override suspend fun getGenres(): SparseArray<String> {
        val networkResponse: GenresResponse = tmdbService.getGenres()
        repoGenres = converter.toGenresList(networkResponse)
        return repoGenres
    }


}

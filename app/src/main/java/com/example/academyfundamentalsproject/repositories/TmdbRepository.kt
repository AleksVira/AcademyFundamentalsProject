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
import timber.log.Timber

interface TmdbRepository {
    suspend fun getTmdbConfig(): Resource<TmdbConfigData>
    suspend fun getGenres(): SparseArray<String>
    fun getPagedNetworkTopRated(): Flow<PagingData<Movie>>
    suspend fun getMovieInfo(movieId: Int): Resource<SingleMovieInfo>
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
                val convertResult = converter.toTmdbConfig(networkResult.value)
                Resource.Success.Value(convertResult)
            }
            is Resource.Failure<*> -> networkResult
            is Resource.Success.Empty -> Resource.Success.Empty
        }
    }


    override fun getPagedNetworkTopRated(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(tmdbService, converter, repoGenres) }
        ).flow
    }


    override suspend fun getMovieInfo(movieId: Int): Resource<SingleMovieInfo> {
        return when (val networkMovieResponse: Resource<MovieInfoResponse> = tmdbService.getMovieInfoById(movieId)) {
            is Resource.Success<*> -> {
                val result = networkMovieResponse.value
                Timber.d("MyTAG_TmdbRepositoryImpl_getMovieInfo(): $result")
                Resource.Success.Value(converter.toSingleMovieInfo(result as MovieInfoResponse))
            }
            is Resource.Failure.Error -> {
                Resource.Failure.Error(networkMovieResponse.error)
            }
            is Resource.Failure.HttpError -> TODO()
            Resource.Success.Empty -> TODO()
        }
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
        Timber.d("MyTAG_TmdbRepositoryImpl_getGenres(): $repoGenres")
        return repoGenres
    }

}
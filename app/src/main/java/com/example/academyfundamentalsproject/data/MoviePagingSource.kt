package com.example.academyfundamentalsproject.data

import android.util.SparseArray
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.academyfundamentalsproject.network.TmdbConverter
import com.example.academyfundamentalsproject.network.TmdbService
import com.example.academyfundamentalsproject.repositories.domain.Movie

class MoviePagingSource(
    private val tmdbService: TmdbService,
    private val converter: TmdbConverter,
    private val repoGenres: SparseArray<String>,
) : PagingSource<Int, Movie>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        //TODO Заменить возвращаемый результат от Injection.tmdbApi.getTopRated(pageNumber):
        // MovieResponse заменить на Resource<MovieResponse> и обработку ошибок сделать тут, вместо try/catch,
        // возвращая LoadResult с ошибкой при неудаче

        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        val networkMoviesResponse = tmdbService.getTopRated(pageNumber)

        val nextKey = if (networkMoviesResponse.results.isNotEmpty()) pageNumber + 1 else null
        val networkMovies = networkMoviesResponse.results

        val prevKey = if (pageNumber > STARTING_PAGE_INDEX) pageNumber - 1 else null

        return LoadResult.Page(
            data = converter.toMoviesList(networkMovies, repoGenres),
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }


}


package com.example.academyfundamentalsproject.network

import android.util.SparseArray
import com.example.academyfundamentalsproject.data.ApiConfig
import com.example.academyfundamentalsproject.data.Images
import com.example.academyfundamentalsproject.network.models.ConfigurationDto
import com.example.academyfundamentalsproject.network.models.GenresResponse
import com.example.academyfundamentalsproject.network.models.ImagesDto
import com.example.academyfundamentalsproject.network.models.TmdbMovieDto
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData

class TmdbConverter {
    fun toApiConfig(configDto: ConfigurationDto): ApiConfig {
        return ApiConfig(
            images = toImages(configDto.images),
            changeKeys = configDto.changeKeys
        )
    }

    private fun toImages(images: ImagesDto): Images {
        return Images(
            baseUrl = images.baseUrl,
            secureBaseUrl = images.secureBaseUrl,
            backdropSizes = images.backdropSizes,
            logoSizes = images.logoSizes,
            posterSizes = images.posterSizes,
            profileSizes = images.profileSizes,
            stillSizes = images.stillSizes
        )
    }

    fun toTmdbConfig(configuration: ConfigurationDto): TmdbConfigData {
        return TmdbConfigData(
            baseUrl = configuration.images.baseUrl,
            secureBaseUrl = configuration.images.secureBaseUrl,
            posterSizes = configuration.images.posterSizes,
            backdropSizes = configuration.images.backdropSizes,
            genres = SparseArray<String>()
        )
    }

    fun toMoviesList(networkMovies: List<TmdbMovieDto>): List<Movie> {
        val moviesList = mutableListOf<Movie>()
        networkMovies.forEach { tmdbMovie ->
            val newMovie = Movie(
                id = tmdbMovie.id,
                movieName = tmdbMovie.originalTitle,
                storyLine = tmdbMovie.overview,
                posterUrl = tmdbMovie.posterPath,
                backdropImageUrl = tmdbMovie.backdropPath,
                ratingPercent = tmdbMovie.voteAverage.toFloat(),
                reviewsCount = tmdbMovie.voteCount,
                pgAge = if (tmdbMovie.adult) 16 else 13,
                movieLengthMinutes = 0,
                genresList = mutableListOf(),
                actorsList = mutableListOf(),
                isLiked = false
            )
            moviesList.add(newMovie)
        }
        return moviesList
    }

    fun toGenresList(networkGenres: GenresResponse): SparseArray<String> {
        val moviesList = SparseArray<String>()
        networkGenres.genres.forEach { genreEntity ->
            moviesList.put(genreEntity.id, genreEntity.name)
        }
        return moviesList
    }
}
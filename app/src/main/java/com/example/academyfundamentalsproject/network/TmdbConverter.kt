package com.example.academyfundamentalsproject.network

import android.util.SparseArray
import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.data.Images
import com.example.academyfundamentalsproject.network.models.*
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.SingleMovieInfo
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData

class TmdbConverter {

/*
    fun toApiConfig(configDto: ConfigurationDto): ApiConfig {
        return ApiConfig(
            images = toImages(configDto.images),
            changeKeys = configDto.changeKeys
        )
    }
*/

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
            avatarSizes = configuration.images.profileSizes,
        )
    }

    fun toMoviesList(networkMovies: List<TmdbMovieDto>, genres: SparseArray<String>): List<Movie> {
        val moviesList = mutableListOf<Movie>()
        networkMovies.forEach { tmdbMovie ->
            val newMovie = Movie(
                id = tmdbMovie.id,
                movieName = tmdbMovie.originalTitle,
                storyLine = tmdbMovie.overview,
                posterUrl = tmdbMovie.posterPath,
                backdropImageUrl = tmdbMovie.backdropPath,
                ratingPercent = (tmdbMovie.voteAverage * 10).toFloat(),
                reviewsCount = tmdbMovie.voteCount,
                pgAge = if (tmdbMovie.adult) 16 else 13,
                movieLengthMinutes = 0,
                genresList = tmdbMovie.genreIds.map {
                    genres.get(it) ?: throw IllegalArgumentException("Genre not found")
                },
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

    fun toSingleMovieInfo(movieResponse: MovieInfoResponse): SingleMovieInfo {
        return SingleMovieInfo(
            id = movieResponse.id ?: -1,
            runtime = movieResponse.runtime ?: -1
        )
    }

    fun toActorsList(networkActorsResponse: CreditsResponse): List<Actor>? {
        val actorsList = mutableListOf<Actor>()
        networkActorsResponse.cast.forEach { cast ->
            val newActor = Actor(
                id = cast.castId,
                name = cast.name,
                imageUrl = cast.profilePath ?: ""
            )
            actorsList.add(newActor)
        }
        return actorsList
    }
}
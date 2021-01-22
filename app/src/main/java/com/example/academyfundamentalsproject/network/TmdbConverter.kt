package com.example.academyfundamentalsproject.network

import com.example.academyfundamentalsproject.data.ApiConfig
import com.example.academyfundamentalsproject.data.Images
import com.example.academyfundamentalsproject.network.models.ConfigurationDto
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
            size = configuration.images.backdropSizes[0],
            filePath = configuration.images.secureBaseUrl
        )
    }

    fun toMoviesList(networkMovies: List<TmdbMovieDto>): List<Movie> {
        val result = mutableListOf<Movie>()
        networkMovies.forEach { tmdbMovie ->
            val newMovie = Movie(
                id = tmdbMovie.id,
                movieName = tmdbMovie.originalTitle,
                storyLine = tmdbMovie.overview

            )
            result.add(newMovie)
        }
        TODO("Not yet implemented")
    }
}
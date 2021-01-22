package com.example.academyfundamentalsproject.data

import android.content.Context
import com.example.academyfundamentalsproject.repositories.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre(
    val id: Int,
    val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String,
)

@Serializable
private class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    val overview: String,
    val adult: Boolean,
)

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

internal fun parseGenres(data: String): List<Genre> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal fun parseActors(data: String): List<Actor> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map { Actor(id = it.id, name = it.name, imageUrl = it.profilePicture) }
}

internal suspend fun loadFakeMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovies(data, genresMap, actorsMap)
}

internal fun parseMovies(
    data: String,
    genreData: List<Genre>,
    actors: List<Actor>,
): List<Movie> {
    val genresMap = genreData.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        (Movie(
        id = jsonMovie.id,
        movieName = jsonMovie.title,
        storyLine = jsonMovie.overview,
        imageUrl = jsonMovie.posterPicture,
        detailImageUrl = jsonMovie.backdropPicture,
        ratingPercent = jsonMovie.ratings * 10,
        reviewsCount = jsonMovie.votesCount,
        pgAge = if (jsonMovie.adult) 16 else 13,
        movieLengthMinutes = jsonMovie.runtime,
        genresList = jsonMovie.genreIds.map {
            genresMap[it] ?: throw IllegalArgumentException("Genre not found")
        },
        actorsList = jsonMovie.actors.map {
            actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
        },
        isLiked = false
    ))
    }
}
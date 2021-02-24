package com.example.academyfundamentalsproject.network.models


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TmdbMovieDto(
    @SerialName("adult")
    val adult: Boolean, // false
    @SerialName("backdrop_path")
    val backdropPath: String?, // /fQq1FWp1rC89xDrRMuyFJdFUdMd.jpg
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("id")
    val id: Int, // 761053
    @SerialName("original_language")
    val originalLanguage: String, // en
    @SerialName("original_title")
    val originalTitle: String, // Gabriel's Inferno Part III
    @SerialName("overview")
    val overview: String, // The final part of the film adaption of the erotic romance novel Gabriel's Inferno written by an anonymous Canadian author under the pen name Sylvain Reynard.
    @SerialName("popularity")
    val popularity: Double, // 34.633
    @SerialName("poster_path")
    val posterPath: String, // /fYtHxTxlhzD4QWfEbrC1rypysSD.jpg
    @SerialName("release_date")
    val releaseDate: String, // 2020-11-19
    @SerialName("title")
    val title: String, // Gabriel's Inferno Part III
    @SerialName("video")
    val video: Boolean, // false
    @SerialName("vote_average")
    val voteAverage: Double, // 9.1
    @SerialName("vote_count")
    val voteCount: Int // 624
)
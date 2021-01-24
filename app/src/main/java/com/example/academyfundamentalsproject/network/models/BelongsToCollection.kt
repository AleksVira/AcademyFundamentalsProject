package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class BelongsToCollection(
    @SerialName("id")
    val id: Int, // 729322
    @SerialName("name")
    val name: String, // Gabriel's Inferno Collection
    @SerialName("poster_path")
    val posterPath: String, // /LdSn17U6ybhtPJT3S6fTNRni5Y.jpg
    @SerialName("backdrop_path")
    val backdropPath: String // /hXF55codODfnzTZDExbUbfFmA9y.jpg
)
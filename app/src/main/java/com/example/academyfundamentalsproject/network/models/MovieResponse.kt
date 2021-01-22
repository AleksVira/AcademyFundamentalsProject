package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class MovieResponse(
    @SerialName("page")
    val page: Int, // 1
    @SerialName("results")
    val results: List<TmdbMovieDto>, // listOf()
    @SerialName("total_pages")
    val totalPages: Int, // 415
    @SerialName("total_results")
    val totalResults: Int // 8284
)
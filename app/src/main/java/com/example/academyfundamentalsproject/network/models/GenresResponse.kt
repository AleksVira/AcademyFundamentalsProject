package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genres: List<GenresElement>
)
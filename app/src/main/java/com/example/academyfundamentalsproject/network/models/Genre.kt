package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Genre(
    @SerialName("id")
    val id: Int, // 10749
    @SerialName("name")
    val name: String // Romance
)
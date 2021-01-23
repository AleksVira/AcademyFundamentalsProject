package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class GenresElement(
    @SerialName("id")
    val id: Int, // 28
    @SerialName("name")
    val name: String // Action
)
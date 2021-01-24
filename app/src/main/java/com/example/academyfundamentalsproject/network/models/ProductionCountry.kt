package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String, // US
    @SerialName("name")
    val name: String // United States of America
)
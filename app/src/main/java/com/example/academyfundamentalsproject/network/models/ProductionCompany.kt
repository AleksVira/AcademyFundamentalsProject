package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class ProductionCompany(
    @SerialName("id")
    val id: Int, // 92153
    @SerialName("logo_path")
    val logoPath: String?, // /psjvYkjjgAPtS8utnFYDM8t8yi7.png
    @SerialName("name")
    val name: String, // PassionFlix
    @SerialName("origin_country")
    val originCountry: String // US
)
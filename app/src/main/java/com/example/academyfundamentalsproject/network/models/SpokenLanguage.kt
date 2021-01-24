package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String, // English
    @SerialName("iso_639_1")
    val iso6391: String, // en
    @SerialName("name")
    val name: String // English
)
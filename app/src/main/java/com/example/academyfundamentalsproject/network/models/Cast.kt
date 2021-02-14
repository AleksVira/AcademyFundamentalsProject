package com.example.academyfundamentalsproject.network.models


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Cast(
    @SerialName("adult")
    val adult: Boolean, // false
    @SerialName("gender")
    val gender: Int, // 2
    @SerialName("id")
    val id: Int, // 225730
    @SerialName("name")
    val name: String, // Ryunosuke Kamiki
    @SerialName("original_name")
    val originalName: String, // Ryunosuke Kamiki
    @SerialName("profile_path")
    val profilePath: String?, // /6MwcfIUraHuKOmwjT4gX8BBB2T6.jpg
    @SerialName("cast_id")
    val castId: Int, // 28
    @SerialName("character")
    val character: String, // Taki Tachibana (voice)
)
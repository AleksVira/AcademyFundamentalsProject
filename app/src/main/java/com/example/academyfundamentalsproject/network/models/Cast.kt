package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Cast(
    @SerialName("adult")
    val adult: Boolean, // false
    @SerialName("gender")
    val gender: Int, // 2
    @SerialName("id")
    val id: Int, // 225730
    @SerialName("known_for_department")
    val knownForDepartment: String, // Acting
    @SerialName("name")
    val name: String, // Ryunosuke Kamiki
    @SerialName("original_name")
    val originalName: String, // Ryunosuke Kamiki
    @SerialName("popularity")
    val popularity: Double, // 1.919
    @SerialName("profile_path")
    val profilePath: String?, // /6MwcfIUraHuKOmwjT4gX8BBB2T6.jpg
    @SerialName("cast_id")
    val castId: Int, // 28
    @SerialName("character")
    val character: String, // Taki Tachibana (voice)
    @SerialName("credit_id")
    val creditId: String, // 58a0634e9251412603006efc
    @SerialName("order")
    val order: Int // 0
)
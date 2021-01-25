package com.example.academyfundamentalsproject.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Crew(
    @SerialName("adult")
    val adult: Boolean, // false
    @SerialName("gender")
    val gender: Int, // 0
    @SerialName("id")
    val id: Int, // 19603
    @SerialName("known_for_department")
    val knownForDepartment: String, // Visual Effects
    @SerialName("name")
    val name: String, // Takeshi Imamura
    @SerialName("original_name")
    val originalName: String, // Takeshi Imamura
    @SerialName("popularity")
    val popularity: Double, // 1.094
    @SerialName("profile_path")
    val profilePath: String?, // null
    @SerialName("credit_id")
    val creditId: String, // 5bd201fbc3a3687ac200037f
    @SerialName("department")
    val department: String, // Visual Effects
    @SerialName("job")
    val job: String // Key Animation
)
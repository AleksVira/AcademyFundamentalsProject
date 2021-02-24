package com.example.academyfundamentalsproject.network.models


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CreditsResponse(
    @SerialName("id")
    val id: Int, // 372058
    @SerialName("cast")
    val cast: List<Cast>,
)
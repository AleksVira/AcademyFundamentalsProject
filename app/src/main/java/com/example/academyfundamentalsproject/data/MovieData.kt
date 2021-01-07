package com.example.academyfundamentalsproject.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class MovieData(
    val id: Int = -1,
    val movieName: String = "JOHN DOE film",
    val storyLine: String = "Bla-bla-bla",
    val imageUrl: String = "",
    val detailImageUrl: String = "",
    val ratingPercent: Float = 0f,
    val reviewsCount: Int = 0,
    val pgAge: Int = 0,
    val movieLengthMinutes: Int = 0,
    val genresList: List<GenreData> = listOf(),
    val actorsList: List<ActorData> = listOf(),
    var isLiked: Boolean = false
) : Parcelable

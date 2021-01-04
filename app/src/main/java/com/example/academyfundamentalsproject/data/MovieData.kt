package com.example.academyfundamentalsproject.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieData(
    val parentalRating: Int = 0,
    var isLiked: Boolean = false,
    val genre: String = "Unknown genre",
    val ratingPercent: Float = 0f,
    val reviewsCount: Int = 0,
    val movieName: String = "JOHN DOE film",
    val movieLengthMinutes: Int = 0,
    val movieDescription: String = "Bla-bla-bla",
    @DrawableRes
    val moviePicturesResource: Int = -1,
    val actorsList: List<Actor> = listOf()
) : Parcelable

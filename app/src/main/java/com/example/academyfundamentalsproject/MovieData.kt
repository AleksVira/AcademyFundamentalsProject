package com.example.academyfundamentalsproject

import androidx.annotation.DrawableRes

data class MovieData(
    val parentalRating: Int = 0,
    val isLiked: Boolean = false,
    val genre: String = "",
    val ratingPercent: Float = 0f,
    val reviewsCount: Int = 0,
    val movieName: String = "",
    val movieLengthMinutes: Int = 0,
    @DrawableRes
    val moviePicturesResource: Int = -1
)

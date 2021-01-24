package com.example.academyfundamentalsproject.repositories.domain

import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.data.Genre

data class Movie(
    val id: Int,
    val movieName: String,
    val storyLine: String,
    var posterUrl: String,
    var backdropImageUrl: String,
    val ratingPercent: Float,
    val reviewsCount: Int,
    val pgAge: Int,
    var movieLengthMinutes: Int,
    val genresList: List<Genre>,
    val actorsList: List<Actor>,
    var isLiked: Boolean,
)

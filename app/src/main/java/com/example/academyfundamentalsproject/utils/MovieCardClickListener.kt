package com.example.academyfundamentalsproject.utils

import com.example.academyfundamentalsproject.data.MovieData

interface MovieCardClickListener {

    fun onMovieCardClicked(movieData: MovieData)
}
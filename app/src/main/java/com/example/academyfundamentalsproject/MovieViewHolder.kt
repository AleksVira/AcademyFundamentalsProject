package com.example.academyfundamentalsproject

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding

class MovieViewHolder(private val movieBinding: ViewHolderMovieBinding) : RecyclerView.ViewHolder(movieBinding.root) {

    fun bindMovie(movieData: MovieData) {
        val cardContext = movieBinding.root.context
        with(movieBinding) {
            itemMoviePicture.setBackgroundResource(movieData.moviePicturesResource)
            parentalGuidanceMark.text = cardContext.getString(R.string.plus_sign, movieData.parentalRating.toString())
            movieLike.visibility = if (movieData.isLiked) VISIBLE else GONE
            itemTagline.text = movieData.genre
            itemMovieRating.setRating(movieData.ratingPercent)
            reviewsCounter.text = cardContext.getString(R.string.reviews_counter, movieData.reviewsCount.toString())
            movieName.text = movieData.movieName
            lengthMin.text = cardContext.getString(R.string.minutes_counter, movieData.movieLengthMinutes.toString())
        }
    }

}
package com.example.academyfundamentalsproject.main_list

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding

class MovieViewHolder(
    private val movieBinding: ViewHolderMovieBinding,
    private val onFavoriteClick: (MovieData, View) -> Unit,
    private val movieCardClickListener: (MovieData) -> Unit,
) : RecyclerView.ViewHolder(movieBinding.root) {

    fun bindMovie(movieData: MovieData) {
        val cardContext = movieBinding.root.context
        with(movieBinding) {
            itemMoviePicture.setBackgroundResource(movieData.moviePicturesResource)
            parentalGuidanceMark.text =
                cardContext.getString(R.string.plus_sign, movieData.parentalRating.toString())
            setFavouriteState(movieData.isLiked)
            itemTagline.text = movieData.genre
            itemMovieRating.setRating(movieData.ratingPercent)
            reviewsCounter.text =
                cardContext.getString(R.string.reviews_counter, movieData.reviewsCount.toString())
            movieName.text = movieData.movieName
            lengthMin.text = cardContext.getString(R.string.minutes_counter,
                movieData.movieLengthMinutes.toString())

            movieLike.setOnClickListener { view ->
                movieData.isLiked = !movieData.isLiked
                setFavouriteState(movieData.isLiked)
                onFavoriteClick.invoke(movieData, view)
            }
            root.setOnClickListener { _ ->
                movieCardClickListener.invoke(movieData)
            }
        }
    }

    private fun setFavouriteState(liked: Boolean) {
        movieBinding.movieLike.setColorFilter(if (liked) ContextCompat.getColor(movieBinding.root.context,
            R.color.like_color) else ContextCompat.getColor(movieBinding.root.context,
            R.color.white))
    }
}
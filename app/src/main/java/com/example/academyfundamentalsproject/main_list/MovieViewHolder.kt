package com.example.academyfundamentalsproject.main_list

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.Movie
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding

class MovieViewHolder(
    private val movieBinding: ViewHolderMovieBinding,
    private val onFavoriteClick: (Movie) -> Unit,
    private val movieCardClickListener: (Movie) -> Unit,
) : RecyclerView.ViewHolder(movieBinding.root) {

    fun bindMovie(movie: Movie) {
        val cardContext = movieBinding.root.context

        val genreString = movie.genresList.joinToString { genre -> genre.name }

        with(movieBinding) {

            Glide.with(cardContext)
                .load(movie.imageUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .fallback(R.drawable.ic_avatar_placeholder)
                .into(ivItemMoviePicture)

            tvParentalGuidanceMark.text =
                cardContext.getString(R.string.plus_sign, movie.pgAge)
            setFavouriteState(movie.isLiked)
            tvItemTagLine.text = genreString
            customItemMovieRating.setRating(movie.ratingPercent)
            tvReviewsCounter.text =
                cardContext.getString(R.string.reviews_counter, movie.reviewsCount)
            tvMovieName.text = movie.movieName
            tvLengthMin.text = cardContext.getString(R.string.minutes_counter, movie.movieLengthMinutes)

            ivMovieLike.setOnClickListener { _ ->
                // Логика обработки смены состояния "isLiked" убрана из ViewHolder-а
                onFavoriteClick(movie)
            }
            root.setOnClickListener { _ ->
                movieCardClickListener(movie)
            }
        }
    }

    private fun setFavouriteState(liked: Boolean) {
        val colorRes = if (liked) {
            R.color.like_color
        } else {
            R.color.white
        }
        movieBinding.ivMovieLike.setColorFilter(ContextCompat.getColor(movieBinding.root.context, colorRes))
    }

    fun bindOnlyFavourite(newLikeState: Boolean) {
        setFavouriteState(newLikeState)
    }
}
package com.example.academyfundamentalsproject.main_list

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding
import timber.log.Timber

class MovieViewHolder(
    private val movieBinding: ViewHolderMovieBinding,
    private val onFavoriteClick: (Int, View) -> Unit,
    private val movieCardClickListener: (Int) -> Unit,
) : RecyclerView.ViewHolder(movieBinding.root) {

    fun bindMovie(movieData: MovieData) {
        val cardContext = movieBinding.root.context

        val genreString = movieData.genresList.joinToString { genre -> genre.name }

        with(movieBinding) {

            Glide.with(cardContext)
                .load(movieData.imageUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .fallback(R.drawable.ic_avatar_placeholder)
                .into(itemMoviePicture)

            parentalGuidanceMark.text =
                cardContext.getString(R.string.plus_sign, movieData.pgAge.toString())
            setFavouriteState(movieData.isLiked)
            itemTagline.text = genreString
            itemMovieRating.setRating(movieData.ratingPercent)
            reviewsCounter.text =
                cardContext.getString(R.string.reviews_counter, movieData.reviewsCount.toString())
            movieName.text = movieData.movieName
            lengthMin.text = cardContext.getString(R.string.minutes_counter,
                movieData.movieLengthMinutes.toString())

            movieLike.setOnClickListener { view ->
                movieData.isLiked = !movieData.isLiked
                setFavouriteState(movieData.isLiked)
                onFavoriteClick.invoke(absoluteAdapterPosition, view)
            }
            root.setOnClickListener { _ ->
                Timber.d("MyTAG_MovieViewHolder_bindMovie(): POSITION = $absoluteAdapterPosition")
                movieCardClickListener.invoke(absoluteAdapterPosition)
            }
        }
    }

    private fun setFavouriteState(liked: Boolean) {
        movieBinding.movieLike.setColorFilter(if (liked) ContextCompat.getColor(movieBinding.root.context,
            R.color.like_color) else ContextCompat.getColor(movieBinding.root.context,
            R.color.white))
    }
}
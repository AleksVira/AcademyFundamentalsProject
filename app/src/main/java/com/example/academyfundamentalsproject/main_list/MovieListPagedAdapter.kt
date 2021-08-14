package com.example.academyfundamentalsproject.main_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding
import com.example.academyfundamentalsproject.main_list.MovieItemDiffCallback.Companion.LIKE
import com.example.academyfundamentalsproject.main_list.MovieItemDiffCallback.Companion.RUNTIME
import com.example.academyfundamentalsproject.repositories.domain.Movie
import timber.log.Timber

//TODO Здесь некорректная реализация DiffUtil, потому что не должно использоваться notifyItemChanged() !!!


class MovieListPagedAdapter(
    private val movieCardClickListener: (Movie) -> Unit,
    private val onFavoriteClick: (Movie, Int) -> Unit,
) : PagingDataAdapter<Movie, MovieViewHolder>(MovieItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onFavoriteClick, movieCardClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bindMovie(it) }
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isNotEmpty()) {
            val o = payloads[0] as Bundle
            for (key in o.keySet()) {
                when (key) {
                    LIKE -> holder.bindOnlyFavourite(o.getBoolean(key))
                    RUNTIME -> holder.updateRuntime(o.getInt(key))
                }
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    fun updateMovieTime(movie: Movie) {
        val movieInAdapter = snapshot().find {
            it?.id == movie.id
        }
        movieInAdapter?.let {
            val bundle = Bundle()
            bundle.putInt(RUNTIME, movie.movieLengthMinutes)
            it.movieLengthMinutes = movie.movieLengthMinutes

            val index = snapshot().indexOf(it)
            Timber.d("MyTAG_MovieListPagedAdapter_updateMovie(): INDEX = $index ${movieInAdapter.movieLengthMinutes}")
            notifyItemChanged(index, bundle)
        }
    }

    fun updateFavouriteState(index: Int, movie: Movie) {
        val bundle = Bundle()
        bundle.putBoolean(LIKE, movie.isLiked)

        snapshot()[index]?.isLiked = movie.isLiked
        Timber.d("MyTAG_MovieListPagedAdapter_updateFavouriteState(): Favourite INDEX = $index, newState = ${movie.isLiked}")
        notifyItemChanged(index, bundle)
    }

}
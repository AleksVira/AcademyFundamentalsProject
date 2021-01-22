package com.example.academyfundamentalsproject.main_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding
import com.example.academyfundamentalsproject.main_list.MovieItemDiffCallback.Companion.LIKE


class MovieListAdapter(
    private val movieCardClickListener: (Int) -> Unit,
    private val onFavoriteClick: (Int) -> Unit,
) : ListAdapter<Movie, MovieViewHolder>(MovieItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onFavoriteClick, movieCardClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindMovie(movie)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        } else {
            val o = payloads[0] as Bundle
            for (key in o.keySet()) {
                if (key == LIKE) {
                    holder.bindOnlyFavourite(o.getBoolean(key))
                }
            }
        }

        super.onBindViewHolder(holder, position, payloads)
    }
}


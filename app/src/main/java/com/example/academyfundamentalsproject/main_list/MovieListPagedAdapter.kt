package com.example.academyfundamentalsproject.main_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding
import com.example.academyfundamentalsproject.main_list.MovieItemDiffCallback.Companion.LIKE
import com.example.academyfundamentalsproject.main_list.MovieItemDiffCallback.Companion.RUNTIME
import com.example.academyfundamentalsproject.repositories.domain.Movie


class MovieListPagedAdapter(
    private val movieCardClickListener: (Int) -> Unit,
    private val onFavoriteClick: (Int, Int) -> Unit,
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
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        } else {
            val o = payloads[0] as Bundle
            for (key in o.keySet()) {
                when (key) {
                    LIKE -> holder.bindOnlyFavourite(o.getBoolean(key))
                    RUNTIME -> holder.updateRuntime(o.getInt(key))
                }
            }
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun getMovie() {
        this
    }


}
package com.example.academyfundamentalsproject.main_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.databinding.ViewHolderMovieBinding

class MainListAdapter(
    private val movieCardClickListener: (Int) -> Unit,
    private val onFavoriteClick: (Int, View) -> Unit,
) : ListAdapter<MovieData, MovieViewHolder>(MovieItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onFavoriteClick, movieCardClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindMovie(movie)
    }
}


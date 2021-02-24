package com.example.academyfundamentalsproject.main_list

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.academyfundamentalsproject.repositories.domain.Movie

class MovieItemDiffCallback: DiffUtil.ItemCallback<Movie>() {

    companion object {
        const val LIKE: String = "LIKE"
        const val RUNTIME: String = "RUNTIME"
    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return (oldItem == newItem)
    }

    override fun getChangePayload(oldItem: Movie, newItem: Movie): Any? {
        val diff = Bundle()
        if (newItem.isLiked != oldItem.isLiked) {
            diff.putBoolean(LIKE, newItem.isLiked)
        }
        if (newItem.movieLengthMinutes != oldItem.movieLengthMinutes) {
            diff.putInt(RUNTIME, newItem.movieLengthMinutes)
        }
        return if (diff.size() == 0) null else diff
    }
}
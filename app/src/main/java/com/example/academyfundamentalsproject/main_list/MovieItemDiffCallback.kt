package com.example.academyfundamentalsproject.main_list

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.academyfundamentalsproject.data.MovieData

class MovieItemDiffCallback: DiffUtil.ItemCallback<MovieData>() {

    companion object {
        const val LIKE: String = "LIKE"
    }

    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return (oldItem == newItem)
    }

    override fun getChangePayload(oldItem: MovieData, newItem: MovieData): Any? {
        val diff = Bundle()
        if (newItem.isLiked != oldItem.isLiked) {
            diff.putBoolean(LIKE, newItem.isLiked)
        }
        return if (diff.size() == 0) null else diff
    }
}

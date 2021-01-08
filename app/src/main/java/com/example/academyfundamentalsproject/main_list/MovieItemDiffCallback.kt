package com.example.academyfundamentalsproject.main_list

import androidx.recyclerview.widget.DiffUtil
import com.example.academyfundamentalsproject.data.MovieData
import timber.log.Timber

object MovieItemDiffCallback: DiffUtil.ItemCallback<MovieData>() {

    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        val sameItemResult = oldItem == newItem
        Timber.d("MyTAG_MovieItemDiffCallback_areItemsTheSame(): SAME? $sameItemResult")
        return sameItemResult
    }

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        val result =
        (oldItem.id == newItem.id)
        Timber.d("MyTAG_MovieItemDiffCallback_areContentsTheSame(): RESULT = $result")
        return result
    }

}

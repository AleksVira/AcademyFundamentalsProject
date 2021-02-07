package com.example.academyfundamentalsproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.academyfundamentalsproject.repositories.TmdbRepository
import com.example.academyfundamentalsproject.view_models.MoviesViewModel

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(private val tmdbRepository: TmdbRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesViewModel::class.java -> MoviesViewModel(repository = tmdbRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
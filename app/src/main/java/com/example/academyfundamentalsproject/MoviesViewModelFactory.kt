package com.example.academyfundamentalsproject

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.academyfundamentalsproject.view_models.MoviesViewModel
import com.example.academyfundamentalsproject.view_models.TmdbRepository

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(private val application: Application, private val tmdbRepository: TmdbRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesViewModel::class.java -> MoviesViewModel(app = application, repository = tmdbRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
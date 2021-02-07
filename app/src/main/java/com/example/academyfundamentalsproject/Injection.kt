package com.example.academyfundamentalsproject

import com.example.academyfundamentalsproject.network.RetrofitModule
import com.example.academyfundamentalsproject.network.TmdbConverter
import com.example.academyfundamentalsproject.repositories.TmdbRepository
import com.example.academyfundamentalsproject.repositories.TmdbRepositoryImpl

object Injection {
    val tmdbApi = RetrofitModule.tmdbService
    val tmdbRepository: TmdbRepository by lazy { TmdbRepositoryImpl(tmdbApi, TmdbConverter()) }
//    val moviesViewModelFactory: MoviesViewModelFactory by lazy { MoviesViewModelFactory(tmdbRepository) }
}
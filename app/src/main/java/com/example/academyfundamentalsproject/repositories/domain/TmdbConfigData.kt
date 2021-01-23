package com.example.academyfundamentalsproject.repositories.domain

import android.util.SparseArray

data class TmdbConfigData(
    val baseUrl: String,
    val secureBaseUrl: String,
    val posterSizes: List<String>,
    val backdropSizes: List<String>,
    var genres: SparseArray<String>
)
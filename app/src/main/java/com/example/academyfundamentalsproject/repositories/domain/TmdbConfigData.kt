package com.example.academyfundamentalsproject.repositories.domain

data class TmdbConfigData(
    val baseUrl: String,
    val secureBaseUrl: String,
    val posterSizes: List<String>,
    val backdropSizes: List<String>,
    val avatarSizes: List<String>,
)
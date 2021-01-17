package com.example.academyfundamentalsproject.data

data class Images(
    val baseUrl: String,
    val posterSizes: List<String>,
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val logoSizes: List<String>,
    val stillSizes: List<String>,
    val profileSizes: List<String>
)
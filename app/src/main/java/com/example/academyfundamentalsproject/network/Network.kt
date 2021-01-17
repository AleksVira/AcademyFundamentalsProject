package com.example.academyfundamentalsproject.network

class Network(
    private val tmdbApi: TmdbApi,
    private val tmdbConverter: TmdbConverter
): NetworkTmdb {

    override suspend fun getApiConfig() {
        TODO("Not yet implemented")
    }
}
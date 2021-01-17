package com.example.academyfundamentalsproject.network

import com.example.academyfundamentalsproject.network.RetrofitModule.API_KEY_HEADER
import com.example.academyfundamentalsproject.network.RetrofitModule.apiKey
import okhttp3.Interceptor
import okhttp3.Response


class TmdbApiHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(API_KEY_HEADER, apiKey)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
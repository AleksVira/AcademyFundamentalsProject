package com.example.academyfundamentalsproject.network

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?

}

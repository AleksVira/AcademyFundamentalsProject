package com.example.academyfundamentalsproject.data

import com.example.academyfundamentalsproject.network.models.ImagesDto

data class ApiConfig(
    val images: Images,
    val changeKeys: List<String>
)
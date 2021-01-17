package com.example.academyfundamentalsproject.network

import com.example.academyfundamentalsproject.data.ApiConfig
import com.example.academyfundamentalsproject.data.Images
import com.example.academyfundamentalsproject.network.models.ConfigurationModelDto
import com.example.academyfundamentalsproject.network.models.ImagesDto

class TmdbConverter {
    fun toApiConfig(configDto: ConfigurationModelDto): ApiConfig {
        return ApiConfig(
            images = toImages(configDto.images),
            changeKeys = configDto.changeKeys
        )
    }

    private fun toImages(images: ImagesDto): Images {
        return Images(
            baseUrl = images.baseUrl,
            posterSizes = images.posterSizes,
            secureBaseUrl = images.secureBaseUrl,
            backdropSizes = images.backdropSizes,
            logoSizes = images.logoSizes,
            stillSizes = images.stillSizes,
            profileSizes = images.profileSizes
        )
    }
}
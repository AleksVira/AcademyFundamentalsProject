package com.example.academyfundamentalsproject.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationDto(
	val images: ImagesDto,
	@SerialName("change_keys")
	val changeKeys: List<String>
)

@Serializable
data class ImagesDto(
	@SerialName("base_url")
	val baseUrl: String,
	@SerialName("secure_base_url")
	val secureBaseUrl: String,
	@SerialName("poster_sizes")
	val posterSizes: List<String>,
	@SerialName("backdrop_sizes")
	val backdropSizes: List<String>,
	@SerialName("logo_sizes")
	val logoSizes: List<String>,
	@SerialName("still_sizes")
	val stillSizes: List<String>,
	@SerialName("profile_sizes")
	val profileSizes: List<String>
)


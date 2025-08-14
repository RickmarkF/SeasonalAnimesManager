package com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_details

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Long,
    val name: String
)
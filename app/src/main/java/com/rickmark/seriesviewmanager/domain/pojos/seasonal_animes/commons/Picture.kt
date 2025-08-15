package com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.commons

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val medium: String,
    val large: String
)
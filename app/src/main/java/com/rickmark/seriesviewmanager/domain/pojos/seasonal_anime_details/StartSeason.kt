package com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_details

import kotlinx.serialization.Serializable

@Serializable
data class StartSeason(
    val year: Long,
    val season: String
)
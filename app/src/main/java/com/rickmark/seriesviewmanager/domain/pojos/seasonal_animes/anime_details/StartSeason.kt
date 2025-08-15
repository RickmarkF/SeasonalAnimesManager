package com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_details

import kotlinx.serialization.Serializable

@Serializable
data class StartSeason(
    val year: Long,
    val season: String
)
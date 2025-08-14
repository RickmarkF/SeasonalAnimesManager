package com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_list

import kotlinx.serialization.Serializable

@Serializable
data class AnimeSeasonList(
    val data: List<Data>
)


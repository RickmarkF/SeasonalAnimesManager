package com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_details

import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.commons.Node
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recommendation(
    val node: Node,

    @SerialName("num_recommendations")
    val numRecommendations: Long
)

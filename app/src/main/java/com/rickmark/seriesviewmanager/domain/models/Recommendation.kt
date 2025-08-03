package com.rickmark.seriesviewmanager.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recommendation(
    val node: Node,

    @SerialName("num_recommendations")
    val numRecommendations: Long
)

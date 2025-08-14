package com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_details

import com.rickmark.seriesviewmanager.domain.pojos.Node
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatedAnime(
    val node: Node,

    @SerialName("relation_type")
    val relationType: String,

    @SerialName("relation_type_formatted")
    val relationTypeFormatted: String
)

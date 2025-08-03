package com.rickmark.seriesviewmanager.domain.models

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

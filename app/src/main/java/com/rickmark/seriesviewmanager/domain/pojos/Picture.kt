package com.rickmark.seriesviewmanager.domain.pojos

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val medium: String,
    val large: String
)
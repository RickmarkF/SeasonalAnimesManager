package com.rickmark.seriesviewmanager.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val medium: String,
    val large: String
)
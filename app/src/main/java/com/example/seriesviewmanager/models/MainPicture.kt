package com.example.seriesviewmanager.models

import kotlinx.serialization.Serializable

@Serializable
data class MainPicture(
    val medium: String,
    val large: String
)


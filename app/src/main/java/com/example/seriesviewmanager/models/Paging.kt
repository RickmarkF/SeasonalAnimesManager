package com.example.seriesviewmanager.models

import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    val previous: String?,
    val next: String?
)

package com.rickmark.seriesviewmanager.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class Data @OptIn(ExperimentalSerializationApi::class) constructor(
    val node: Node
)


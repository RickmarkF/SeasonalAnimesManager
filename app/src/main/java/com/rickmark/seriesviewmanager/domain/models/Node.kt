package com.rickmark.seriesviewmanager.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@kotlinx.serialization.ExperimentalSerializationApi
data class Node constructor(
    val id: Int,
    val title: String,
    @JsonNames("main_picture") val mainPicture: MainPicture? = null
)


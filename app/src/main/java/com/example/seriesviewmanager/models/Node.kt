package com.example.seriesviewmanager.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Node @OptIn(ExperimentalSerializationApi::class)
constructor(
    val id: Int,
    val title: String,
    @JsonNames("main_picture") val mainPicture: MainPicture
)


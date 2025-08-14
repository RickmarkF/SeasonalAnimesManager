package com.rickmark.seriesviewmanager.domain.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Node(
    val id: Long,
    val title: String,
    @SerialName("main_picture")
    val mainPicture: Picture
)


package com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.commons

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Node(
    val id: Long,
    val title: String,
    @SerialName("main_picture")
    val mainPicture: Picture
)


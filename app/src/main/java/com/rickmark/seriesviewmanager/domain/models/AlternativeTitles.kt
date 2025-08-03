package com.rickmark.seriesviewmanager.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlternativeTitles(
    val synonyms: ArrayList<String>,

    @SerialName("en")
    val english: String,

    @SerialName("ja")
    val japanese: String
)
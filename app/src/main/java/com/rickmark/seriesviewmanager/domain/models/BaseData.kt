package com.rickmark.seriesviewmanager.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseData(
    val data: List<Data>
)


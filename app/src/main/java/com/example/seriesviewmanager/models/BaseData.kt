package com.example.seriesviewmanager.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseData(
    val data: List<Data>,
    val paging: Paging
)


package com.rickmark.seriesviewmanager.data.request

import com.rickmark.seriesviewmanager.domain.constants.HttpEndpoints
import com.rickmark.seriesviewmanager.domain.constants.HttpProperties
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.AnimeListData
import com.rickmark.seriesviewmanager.domain.models.Data
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder

class RequestManager {

    private val token: String = "ad1162093716f04f8cba96898a43d093";

    fun getAnime(animeName: String): AnimeListData? {
        val url: String = HttpEndpoints.MY_ANIME_LIST_BASE_URL + "anime"
        val jsonBuilder: Json = Json(builderAction = getJsonBuilder())
        var animeListData: AnimeListData? = null

        runBlocking {
            launch {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                    install(ContentNegotiation) { json(jsonBuilder) }
                }
                val request = prepareRequest(animeName)
                animeListData = client
                    .get(url, request)
                    .body(TypeInfo(AnimeListData::class))
            }
        }
        return animeListData
    }

    fun getSeasonalAnime(season: String, year: Int): List<Data>? {
        val url: String = HttpEndpoints.MY_ANIME_LIST_BASE_URL + "anime/season/${year}/${season}"
        val jsonBuilder: Json = Json(builderAction = getJsonBuilder())
        var baseData: List<Data>? = null
        runBlocking {
            launch {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                    install(ContentNegotiation) { json(jsonBuilder) }
                }

                val request = prepareRequest2()
                val result: AnimeListData = client.get(url, request).body()
                baseData = result.data
            }

        }

        return baseData
    }

    fun getAnimeDetails(id: Int?): AnimeDetails? {
        val url: String = HttpEndpoints.MY_ANIME_LIST_BASE_URL + "anime/${id}"
        val jsonBuilder: Json = Json(builderAction = getJsonBuilder())
        var baseData: AnimeDetails? = null

        runBlocking {
            launch {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                    install(ContentNegotiation) { json(jsonBuilder) }
                }
                val request = prepareRequest3()

                baseData = client
                    .get(url, request)
                    .body(TypeInfo(AnimeDetails::class))
            }
        }
        return baseData
    }

    private fun getJsonBuilder(): JsonBuilder.() -> Unit = {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private fun prepareRequest(animeName: String): HttpRequestBuilder.() -> Unit = {
        parameter("q", animeName)
        parameter("limit", 30)
        headers {
            append(HttpProperties.MY_ANIME_LIST_HEADER_ID, token)
        }
    }

    private fun prepareRequest2(): HttpRequestBuilder.() -> Unit = {
        parameter("limit", 500)
        headers {
            append(HttpProperties.MY_ANIME_LIST_HEADER_ID, token)
        }
    }

    private fun prepareRequest3(): HttpRequestBuilder.() -> Unit = {
        parameter(
            "fields", "title," +
                    "main_picture," +
                    "alternative_titles," +
                    "start_date," +
                    "end_date," +
                    "synopsis," +
                    "media_type," +
                    "status," +
                    "genres," +
                    "num_episodes," +
                    "start_season," +
                    "source," +
                    "average_episode_duration," +
                    "pictures," +
                    "related_anime," +
                    "related_manga," +
                    "recommendations," +
                    "studios"
        )
        // parameter("fields","title,synopsis")
        headers {
            append(HttpProperties.MY_ANIME_LIST_HEADER_ID, token)
        }
    }

}


package com.rickmark.seriesviewmanager.data.request

import com.rickmark.seriesviewmanager.domain.constants.HttpEndpoints
import com.rickmark.seriesviewmanager.domain.constants.HttpProperties
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.AnimeListData
import com.rickmark.seriesviewmanager.domain.models.Data
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder

class RequestManager {

    private val token: String = "ad1162093716f04f8cba96898a43d093";

    suspend fun getAnime(animeName: String): AnimeListData? {
        val url: String = HttpEndpoints.MY_ANIME_LIST_BASE_URL + "anime"
        var animeListData: AnimeListData? = null
        val client = getHttpClient()
        val request = prepareRequest(animeName)


        animeListData = client
            .get(url, request)
            .body(TypeInfo(AnimeListData::class))

        return animeListData
    }

    suspend fun getSeasonalAnime(season: String, year: Int): List<Data>? {
        val url: String = HttpEndpoints.MY_ANIME_LIST_BASE_URL + "anime/season/${year}/${season}"
        var baseData: List<Data>? = null
        val client = getHttpClient()
        val request = prepareRequest2()
        val result: AnimeListData = client.get(url, request).body()
        baseData = result.data

        return baseData
    }

    suspend fun getAnimeDetails(id: Int?): AnimeDetails? {
        val url: String = HttpEndpoints.MY_ANIME_LIST_BASE_URL + "anime/${id}"
        var baseData: AnimeDetails? = null
        val client = getHttpClient()
        val request = prepareRequest3()

        baseData = client
            .get(url, request)
            .body(TypeInfo(AnimeDetails::class))

        return baseData
    }

    private fun getHttpClient(): HttpClient = HttpClient(CIO) {
        install(HttpTimeout) {
            connectTimeoutMillis = 30000  // 15 segundos
            socketTimeoutMillis = 30000
            requestTimeoutMillis = 30000
        }
        install(ContentNegotiation) {
            json(Json(builderAction = getJsonBuilder()))
        }
        expectSuccess = true
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
        headers {
            append(HttpProperties.MY_ANIME_LIST_HEADER_ID, token)
        }
    }

}


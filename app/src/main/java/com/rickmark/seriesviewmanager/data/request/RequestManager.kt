package com.rickmark.seriesviewmanager.data.request

import com.rickmark.seriesviewmanager.domain.Constants
import com.rickmark.seriesviewmanager.domain.models.BaseData
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
import java.net.URLEncoder

class RequestManager {

    private val token: String = "ad1162093716f04f8cba96898a43d093";

    fun getRequest(animeName: String): BaseData? {
        val url: String = Constants.MY_ANIME_LIST_BASE_URL + "anime"
        val jsonBuilder: Json = Json(builderAction = getJsonBuilder())
        var baseData: BaseData? = null

        runBlocking {
            launch {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                    install(ContentNegotiation) { json(jsonBuilder) }
                }
                baseData = client
                    .get(url, prepareRequest(animeName))
                    .body(TypeInfo(BaseData::class))
            }
        }
        return baseData
    }

    private fun getJsonBuilder(): JsonBuilder.() -> Unit = {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private fun prepareRequest(animeName: String): HttpRequestBuilder.() -> Unit = {
        parameter("q", URLEncoder.encode(animeName, "UTF-8"))
        parameter("limit", 30)
        headers {
            append(Constants.MY_ANIME_LIST_HEADER_ID, token)
        }
    }
}


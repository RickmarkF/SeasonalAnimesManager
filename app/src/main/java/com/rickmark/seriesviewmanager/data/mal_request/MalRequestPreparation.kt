package com.rickmark.seriesviewmanager.data.mal_request

import android.content.res.Resources
import com.rickmark.seriesviewmanager.R
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MalRequestPreparation(val resources: Resources, val token: String) {

    var url: String = ""
        set(value) {
            field = baseUrl.plus(value)
        }
    lateinit var httpRequest: HttpRequestBuilder.() -> Unit
    lateinit var httpClient: HttpClient
    private val baseUrl: String = resources.getString(R.string.my_anime_list_base_url)
    private val malHeaderId: String = resources.getString(R.string.my_anime_list_header_id)


    fun setUrl(url: String): MalRequestPreparation {
        this.url = url
        return this
    }

    fun <T> setHttpRequest(key: String, value: T): MalRequestPreparation {
        this.httpRequest = {
            parameter(key, value)
            headers {
                append(malHeaderId, token)
            }
        }
        return this
    }

    fun setHttpClient(
        connectTimeout: Long = 30000,
        socketTimeout: Long = 30000,
        requestTimeout: Long = 30000
    ): MalRequestPreparation {
        httpClient = HttpClient(CIO) {
            install(HttpTimeout) {
                connectTimeoutMillis = connectTimeout
                socketTimeoutMillis = socketTimeout
                requestTimeoutMillis = requestTimeout
            }
            install(ContentNegotiation) {
                json(Json(builderAction = {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }))
            }
            expectSuccess = true
        }
        return this
    }

    suspend fun buildGetHttpRequest(): HttpResponse {
        if (!::httpClient.isInitialized) {
            setHttpClient()
        }
        val result = httpClient.get(url, httpRequest)
        return result
    }

}


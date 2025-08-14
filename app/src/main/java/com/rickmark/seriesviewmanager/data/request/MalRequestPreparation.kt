package com.rickmark.seriesviewmanager.data.request

import android.content.res.Resources
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.firebase.repository.FirebaseRepository
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
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

class MalRequestPreparation(val resources: Resources,val token: String) {

    var url: String = ""
        set(value) {
            field = baseUrl.plus(value)
        }
    lateinit var request: HttpRequestBuilder.() -> Unit


    private val baseUrl: String = resources.getString(R.string.my_anime_list_base_url)
    private val malHeaderId: String = resources.getString(R.string.my_anime_list_header_id)


    fun setUrl(url: String): MalRequestPreparation {
        this.url = url
        return this
    }

    fun <T> setHttpRequest(key: String, value: T): MalRequestPreparation {
        this.request = prepareHttpRequest(key, value)
        return this
    }

    private fun <T> prepareHttpRequest(key: String, value: T): HttpRequestBuilder.() -> Unit = {
        parameter(key, value)
        headers {
            append(malHeaderId, token)
        }
    }

    private fun getHttpClient(): HttpClient = HttpClient(CIO) {
        install(HttpTimeout) {
            connectTimeoutMillis = 30000  // 15 segundos
            socketTimeoutMillis = 30000
            requestTimeoutMillis = 30000
        }
        install(ContentNegotiation) {
            json(Json(builderAction = {
                ignoreUnknownKeys = true
                prettyPrint = true
            }))
        }
        expectSuccess = true
    }

    suspend fun buildGetHttpRequest(): HttpResponse {
        val client = getHttpClient()
        val result = client.get(url, request)
        return result
    }

}


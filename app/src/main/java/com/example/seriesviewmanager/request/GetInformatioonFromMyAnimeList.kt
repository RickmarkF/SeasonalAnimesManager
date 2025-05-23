package com.example.seriesviewmanager.request

import com.example.seriesviewmanager.models.BaseData
import com.example.seriesviewmanager.models.Data
import com.example.seriesviewmanager.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.net.URLEncoder

class GetInformatioonFromMyAnimeList {

    private val token:String = "ad1162093716f04f8cba96898a43d093";

     fun getRequest(animeName:String): BaseData? {
         val nameFormated = URLEncoder.encode(animeName, "UTF-8")
         var baseData: BaseData? = null
         runBlocking {
            var response: HttpResponse? = null
            val json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }
            launch {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                    install(ContentNegotiation) {
                        json(
                            json
                        )
                    }
                }

                baseData =
                    client.get(Constants.MY_ANIME_LIST_BASE_URL+"anime") {
                        parameter("q",nameFormated)
                        parameter("limit",1)
                        parameter("offset",10)
                        headers {
                            append(Constants.MY_ANIME_LIST_HEADER_ID, token)
                        }
                    }.body(TypeInfo(BaseData::class))
            }
        }
         return baseData
    }
}
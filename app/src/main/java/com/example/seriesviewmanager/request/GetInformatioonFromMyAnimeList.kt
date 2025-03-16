package com.example.seriesviewmanager.request

import com.example.seriesviewmanager.models.BaseData
import com.example.seriesviewmanager.models.Data
import com.example.seriesviewmanager.models.Paging
import com.example.seriesviewmanager.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class GetInformatioonFromMyAnimeList {

    private val token:String = "ad1162093716f04f8cba96898a43d093";

     fun getRequest(animeName:String): BaseData? {
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
                    client.get(Constants.MY_ANIME_LIST_BASE_URL+"anime?q=${animeName}&limit=1&offset=10") {
                        headers {
                            append(Constants.MY_ANIME_LIST_HEADER_ID, token)
                        }
                    }.body(TypeInfo(BaseData::class))
                println(baseData.toString())

                println("data.data[0].node.title")

            }
        }
         return baseData
    }
}
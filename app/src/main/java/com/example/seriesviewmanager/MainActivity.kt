package com.example.seriesviewmanager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seriesviewmanager.server.MyHTTPServer
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private val token:String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getRequest()

        MyHTTPServer(8080).also { it.start()}

    }

    private fun getRequest() {
        runBlocking {
            var response: HttpResponse? = null
            launch {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                }
                response =
                    client.get("https://api.myanimelist.net/v2/anime?q=pokemon&limit=1&offset=10") {
                        headers {
                            append("X-MAL-CLIENT-ID", token)
                        }
                    }

                val content: String = String(response!!.bodyAsBytes())

                println(content)
            }
        }
    }
}
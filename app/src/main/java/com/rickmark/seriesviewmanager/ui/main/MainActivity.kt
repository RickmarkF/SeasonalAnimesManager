package com.rickmark.seriesviewmanager.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.request.HttpRequestMyAnimeList
import com.rickmark.seriesviewmanager.data.server.MyHTTPServer
import com.rickmark.seriesviewmanager.domain.interfaces.ISendHttpRequest

class MainActivity : AppCompatActivity() {

    val escribir: EditText = findViewById(R.id.searchAnimeEditText)
    val textoMostrar: TextView = findViewById(R.id.searchAnimeSeeResult)
    val send: Button = findViewById(R.id.searchAnimeSendRequest)
    val image: ImageView = findViewById(R.id.imageView3)
    val recyclerView: RecyclerView = findViewById(R.id.recicler)

    fun prepareWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        return insets
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main),
            this::prepareWindowInsets
        )

        MyHTTPServer(8080).also { it.start() }
        var request: ISendHttpRequest = HttpRequestMyAnimeList()

        send.setOnClickListener { view ->
            request.sendInfoToMyanimeList(escribir, textoMostrar, image, recyclerView, this)
        }
    }

}
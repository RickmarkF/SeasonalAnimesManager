package com.rickmark.seriesviewmanager.ui

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
import com.rickmark.seriesviewmanager.data.request.HttpRequestSenderMyAnimeList
import com.rickmark.seriesviewmanager.domain.interfaces.IMyAnimeListRequestSender

class SearchAnimeActivity : AppCompatActivity() {
    val describe: EditText = findViewById(R.id.searchAnimeEditText)
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
        setContentView(R.layout.search_anime)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main_search),
            this::prepareWindowInsets
        )

        var request: IMyAnimeListRequestSender = HttpRequestSenderMyAnimeList()

        send.setOnClickListener { view ->
            request.sendInfoToMyanimeList(describe, textoMostrar, image, recyclerView, this)
        }
    }

}
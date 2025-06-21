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
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager

class SearchAnimeActivity : AppCompatActivity() {

    private lateinit var describe: EditText
    private lateinit var textoMostrar: TextView
    private lateinit var send: Button
    private lateinit var image: ImageView
    private lateinit var recyclerView: RecyclerView

    fun prepareWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        return insets
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.search_anime)

        // Inicializar vistas después de setContentView
        describe = findViewById(R.id.searchAnimeEditText)
        textoMostrar = findViewById(R.id.searchAnimeSeeResult)
        send = findViewById(R.id.searchAnimeSendRequest)
        image = findViewById(R.id.imageView3)
        recyclerView = findViewById(R.id.recicler)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main_search),
            this::prepareWindowInsets
        )

        val request: IAnimeManager = AnimeManager()

        send.setOnClickListener {
            request.sendInfoToMyanimeList(describe, textoMostrar, image, recyclerView, this)
        }
    }
}

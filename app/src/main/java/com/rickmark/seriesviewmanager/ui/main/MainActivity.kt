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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.request.GetInformatioonFromMyAnimeList
import com.rickmark.seriesviewmanager.data.server.MyHTTPServer
import com.rickmark.seriesviewmanager.domain.models.BaseData
import com.rickmark.seriesviewmanager.domain.models.Data
import com.rickmark.seriesviewmanager.ui.reciclerViews.CustomAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main),
            this::prepareWindowInsets
        )


        MyHTTPServer(8080).also { it.start() }

        val escribir: EditText = findViewById(R.id.searchAnimeEditText)
        val textoMostrar: TextView = findViewById(R.id.searchAnimeSeeResult)
        val send: Button = findViewById(R.id.searchAnimeSendRequest)


        send.setOnClickListener { view -> sendInfoToMyanimeList(escribir, textoMostrar) }
    }

    fun prepareWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        return insets
    }

    fun sendInfoToMyanimeList(editText: EditText, mostrar: TextView) {
        var animeName: String = editText.text.toString()


        val image: ImageView = findViewById(R.id.imageView3)
        val recyclerView: RecyclerView = findViewById(R.id.recicler)

        val request = GetInformatioonFromMyAnimeList()
        var baseData: BaseData? = request.getRequest(animeName);

        if (baseData != null) {

            var animeList: List<Data> = baseData.data
            var animeInformation: Data? = animeList
                .filter { it.node.title.contains(animeName, true) }
                .firstOrNull { it.node.title.equals(animeName, true) }

            var textToShow: String

            if (animeInformation != null) {
                textToShow = "Nombre:${animeInformation.node.title}"


                val url: String = animeInformation.node.mainPicture.large.toString()
                Glide.with(this).load(url).into(image);

                val customAdapter = CustomAdapter(animeList, applicationContext)

                recyclerView.also {
                    it.layoutManager = LinearLayoutManager(this)
                    it.adapter = customAdapter
                }

            } else {
                textToShow = "El anime $animeName no se ha encontrado"
                image.setImageResource(R.drawable.element_not_found)
            }

            mostrar.text = textToShow
        }


    }
}
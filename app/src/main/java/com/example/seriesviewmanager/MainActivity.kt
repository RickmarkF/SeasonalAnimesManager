package com.example.seriesviewmanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.seriesviewmanager.models.Data
import com.example.seriesviewmanager.request.GetInformatioonFromMyAnimeList
import com.example.seriesviewmanager.server.MyHTTPServer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        MyHTTPServer(8080).also { it.start() }


        val escribir: EditText = findViewById(R.id.searchAnimeEditText)
        val textoMostrar: TextView = findViewById(R.id.searchAnimeSeeResult)
        val send: Button = findViewById(R.id.searchAnimeSendRequest)


        send.setOnClickListener { view -> sendInfoToMyanimeList(escribir, textoMostrar) }

    }


    fun sendInfoToMyanimeList(editText: EditText, mostrar: TextView) {
        var animeName: String = editText.text.toString()
        val image: ImageView = findViewById(R.id.imageView3)

        val info = GetInformatioonFromMyAnimeList()

        val data = info.getRequest(animeName);

        var info2 : Data? = data?.data?.
            filter{it.node.title.contains(animeName,true)}?.
            firstOrNull { it.node.title.equals(animeName,true) }

        if(info2!=null){
            mostrar.text = "Nombre:${info2?.node?.title}"
            val url: String = info2.node.mainPicture.large.toString()
            Glide.with(this).load(url).into(image);
        }else{
            mostrar.text = "El anime: ${info2?.node?.title} no se ha encontrado"
            image.setImageResource(R.drawable.element_not_found)
        }

    }
}
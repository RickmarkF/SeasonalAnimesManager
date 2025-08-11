package com.rickmark.seriesviewmanager.data.request

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.AnimeListData
import com.rickmark.seriesviewmanager.domain.models.Data
import com.rickmark.seriesviewmanager.ui.reciclerViews.CustomAdapter
import kotlinx.serialization.ExperimentalSerializationApi

class AnimeManager : IAnimeManager {

    private var request = RequestManager()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getAnimeFromMyanimeList(
        editText: EditText,
        mostrar: TextView,
        image: ImageView,
        recyclerView: RecyclerView,
        context: Context
    ): Unit {
        var animeName: String = editText.text.toString()
        var animeListData: AnimeListData? = request.getAnime(animeName);

        if (animeListData != null) {

            var animeList: List<Data> = animeListData.data
            var animeInformation: Data? = animeList
                .filter { it.node.title.contains(animeName, true) }
                .firstOrNull { it.node.title.equals(animeName, true) }

            var textToShow: String

            if (animeInformation != null) {
                textToShow = "Nombre:${animeInformation.node.title}"


                val url: String = animeInformation.node.mainPicture?.large.toString()
                Glide.with(context).load(url).into(image);

                val customAdapter = CustomAdapter(animeList, context)

                recyclerView.also {
                    it.layoutManager = LinearLayoutManager(context)
                    it.adapter = customAdapter
                }

            } else {
                textToShow = "El anime $animeName no se ha encontrado"
                image.setImageResource(R.drawable.element_not_found)
            }

            mostrar.text = textToShow
        }


    }

    override suspend fun getSeasonalAnime(season: String, year: Int): List<Data>? {
        return request.getSeasonalAnime(season, year)
    }

    override suspend fun getAnimeDetails(id: Int?): AnimeDetails? {
        return request.getAnimeDetails(id)
    }
}
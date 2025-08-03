package com.rickmark.seriesviewmanager.domain.interfaces

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.Data

interface IAnimeManager {

    fun getAnimeFromMyanimeList(
        editText: EditText,
        mostrar: TextView,
        image: ImageView,
        recyclerView: RecyclerView,
        context: Context
    ): Unit

    fun getSeasonalAnime(
    ): List<Data>?

    fun getAnimeDetails(id: Int?): AnimeDetails?
}
package com.rickmark.seriesviewmanager.domain.interfaces

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.domain.models.BaseData

interface IAnimeManager {

    fun getAnimeFromMyanimeList(
        editText: EditText,
        mostrar: TextView,
        image: ImageView,
        recyclerView: RecyclerView,
        context: Context
    ): Unit

    fun getSeasonalAnime(
    ): BaseData?
}
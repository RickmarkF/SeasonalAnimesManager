package com.rickmark.seriesviewmanager.domain.interfaces

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface IAnimeManager {

    fun sendInfoToMyanimeList(
        editText: EditText,
        mostrar: TextView,
        image: ImageView,
        recyclerView: RecyclerView,
        context: Context
    ): Unit
}
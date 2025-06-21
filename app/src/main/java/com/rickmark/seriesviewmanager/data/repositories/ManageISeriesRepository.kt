package com.rickmark.seriesviewmanager.data.repositories

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rickmark.seriesviewmanager.domain.interfaces.ISeriesManagement

class ManageISeriesRepository : ISeriesManagement {

    val database: FirebaseDatabase = Firebase.database

    override fun searchAnime(animeName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAnime(animeName: String) {
        TODO("Not yet implemented")
    }

    override fun addAnime(animeName: String) {
        TODO("Not yet implemented")
    }

    override fun updateAnime(animeName: String) {
        TODO("Not yet implemented")
    }

    override fun getAnimeList() {
        TODO("Not yet implemented")
    }
}
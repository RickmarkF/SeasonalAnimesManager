package com.rickmark.seriesviewmanager.domain.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface IFarebaseRespository {
    fun writeInFirebase(animeName: String, animeId: Int?)
    fun readFromFirebase(): Task<DataSnapshot?>
    fun readFromFirebase(year: Int, season: String): Task<DataSnapshot?>
    fun updateInFirebase(): Unit
    fun deleteFromFirebase(): Unit


}
package com.rickmark.seriesviewmanager.domain.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query

interface IFarebaseRespository {
    fun writeInFirebase(animeName: String, animeId: Int?)
    fun readFromFirebase(): Task<DataSnapshot?>
    fun updateInFirebase(): Unit
    fun deleteFromFirebase(): Unit


}
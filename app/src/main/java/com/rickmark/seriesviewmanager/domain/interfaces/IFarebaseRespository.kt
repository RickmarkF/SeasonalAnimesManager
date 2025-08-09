package com.rickmark.seriesviewmanager.domain.interfaces

interface IFarebaseRespository {
    fun writeInFirebase(animeName: String, animeId: Int?)
    fun readFromFirebase(): Map<String, String>
    fun updateInFirebase(): Unit
    fun deleteFromFirebase(): Unit


}
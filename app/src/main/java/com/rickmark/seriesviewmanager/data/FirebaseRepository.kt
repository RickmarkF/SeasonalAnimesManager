package com.rickmark.seriesviewmanager.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rickmark.seriesviewmanager.domain.interfaces.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.models.AlternativeTitles

class FirebaseRepository: IFarebaseRespository {

    private val database: DatabaseReference = FirebaseDatabase
        .getInstance("https://seriesviewmanager.europe-west1.firebasedatabase.app/")
        .reference

    private val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    override fun writeInFirebase(animeName: String,animeId: Int?) {
        val safeName = animeName.replace(Regex("[.#$\\[\\]]"), "_")
        val email: String = getUserEmail()
        database.child(email)
            .child(CalendarUtilities.getYear().toString())
            .child(CalendarUtilities.getSeason())
            .child(safeName)
            .setValue(animeId)

    }

    override fun readFromFirebase(): Map<String, String> {
        var animes: Map<String, String> = mapOf()
        val email: String = getUserEmail()
        database.child(email)
            .child(CalendarUtilities.getYear().toString())
            .child(CalendarUtilities.getSeason())
            .get()
            .addOnSuccessListener {
                animes = it.value as Map<String, String>
            }
        return animes
    }

    override fun updateInFirebase() {
        TODO("Not yet implemented")
    }

    override fun deleteFromFirebase() {
        TODO("Not yet implemented")
    }

    private fun getUserEmail(): String {
        var email: String = user?.email.toString()
        if (email.isEmpty()) {
            return ""
        }
        return email.split("@").joinToString(separator = "_"){
            it.replace(".", "_")
        }
    }
}
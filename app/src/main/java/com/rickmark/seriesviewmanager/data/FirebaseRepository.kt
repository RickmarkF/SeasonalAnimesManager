package com.rickmark.seriesviewmanager.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rickmark.seriesviewmanager.domain.interfaces.IFarebaseRespository

class FirebaseRepository : IFarebaseRespository {

    private var database: DatabaseReference = FirebaseDatabase
        .getInstance("https://seriesviewmanager.europe-west1.firebasedatabase.app/")
        .reference

    private val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    override fun writeInFirebase(animeName: String, animeId: Int?) {
        val safeName = animeName.replace(Regex("[.#$\\[\\]]"), "_")
        val email: String = getUserEmail()
        val year: Int = CalendarUtilities.getYear()
        val season: String = CalendarUtilities.getSeason()
        database.child(email)
            .child(year.toString())
            .child(season)
            .child(safeName)
            .setValue(animeId)

    }

    override fun readFromFirebase(): Task<DataSnapshot?> {
        val email: String = getUserEmail()
        val year: Int = CalendarUtilities.getYear()
        val season: String = CalendarUtilities.getSeason()

        val result = database.child(email)
            .child(year.toString())
            .child(season)
            .get()

        return result
    }

    override fun readFromFirebase(year: Int, season: String): Task<DataSnapshot?> {
        val email: String = getUserEmail()
        val result = database.child(email)
            .child(year.toString())
            .child(season)
            .get()
        return result
    }

    override fun updateInFirebase() {
        TODO("Not yet implemented")
    }

    override fun deleteFromFirebase(animeName: String) {
        val safeName = animeName.replace(Regex("[.#$\\[\\]]"), "_")
        val email: String = getUserEmail()
        val year: Int = CalendarUtilities.getYear()
        val season: String = CalendarUtilities.getSeason()
        database.child(email)
            .child(year.toString())
            .child(season)
            .child(safeName)
            .removeValue()
    }

    private fun getUserEmail(): String {
        var email: String = user?.email.toString()
        if (email.isEmpty()) {
            return ""
        }
        return email.split("@").joinToString(separator = "_") {
            it.replace(".", "_")
        }
    }
}
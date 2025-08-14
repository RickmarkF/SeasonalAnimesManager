package com.rickmark.seriesviewmanager.data.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rickmark.seriesviewmanager.data.firebase.authentication.FirebaseUserAuthentication
import com.rickmark.seriesviewmanager.data.utilities.CalendarUtilities
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFirebaseUserAuthenticator

class FirebaseRepository : IFarebaseRespository {

    private var database: DatabaseReference = FirebaseDatabase
        .getInstance("https://seriesviewmanager.europe-west1.firebasedatabase.app/")
        .reference


    private var auth: IFirebaseUserAuthenticator = FirebaseUserAuthentication()

    private var animeNameForFirebase: String = ""
        set(value) {
            field = value.replace(Regex("[.#$\\[\\]]"), "_")
        }

    override fun writeInFirebase(animeName: String, animeId: Int?) {
        animeNameForFirebase = animeName
        getBaseUrl()
            .child(animeNameForFirebase)
            .setValue(animeId)

    }

    override fun readFromFirebase(): Task<DataSnapshot?> {
        val result = getBaseUrl().get()
        return result
    }

    override fun readFromFirebase(year: Int, season: String): Task<DataSnapshot?> {
        val result = getBaseUrl(year, season).get()
        return result
    }

    override fun deleteFromFirebase(animeName: String) {
        animeNameForFirebase = animeName
        getBaseUrl().child(animeNameForFirebase).removeValue()
    }

    override fun getMalToken(): Task<DataSnapshot?> {
        val result = database.child("token").get()
        return result
    }

    private fun getBaseUrl(): DatabaseReference {
        val email: String = formatUserEmail()
        val year: Int = CalendarUtilities.Companion.getYear()
        val season: String = CalendarUtilities.Companion.getSeason()
        val reference: DatabaseReference = database.child(email)
            .child(year.toString())
            .child(season)
        return reference
    }

    private fun getBaseUrl(year: Int, season: String): DatabaseReference {
        val email: String = formatUserEmail()
        val reference: DatabaseReference = database.child(email)
            .child(year.toString())
            .child(season)
        return reference
    }

    private fun formatUserEmail(): String {
        val email: String = auth.getCurrentUser()?.email.toString()
        if (email.isEmpty()) {
            return ""
        }
        return email.split("@").joinToString(separator = "_") {
            it.replace(".", "_")
        }
    }
}
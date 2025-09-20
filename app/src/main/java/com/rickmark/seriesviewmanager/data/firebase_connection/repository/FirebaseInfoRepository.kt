package com.rickmark.seriesviewmanager.data.firebase_connection.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rickmark.seriesviewmanager.data.app_utilities.CalendarUtilities
import com.rickmark.seriesviewmanager.data.firebase_connection.authentication.FirebaseUserAuthentication
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFirebaseUserAuthenticator

class FirebaseInfoRepository : IFarebaseRespository {

    private val FIREBASE_DATABASE_URL: String =
        "https://seriesviewmanager.europe-west1.firebasedatabase.app/"
    private val FIREBASE_MAL_TOKEN_URL: String = "token"
    private val REGEX_FIREBASE_ANIME_NAME: Regex = Regex("[.#$\\[\\]]")
    private val REGEX_FIREBASE_ANIME_NAME_REPLACEMENT: String = "_"


    private val emailFormatted: String
        get() {
            val email = auth.getCurrentUser()?.email ?: return ""
            return email
                .split("@").joinToString("_") {
                    it.replace(".", "_")
                }
        }

    private val database: DatabaseReference
        get() = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).reference

    private var auth: IFirebaseUserAuthenticator = FirebaseUserAuthentication()

    private var animeNameForFirebase: String = ""
        set(value) {
            field = value.replace(
                REGEX_FIREBASE_ANIME_NAME,
                REGEX_FIREBASE_ANIME_NAME_REPLACEMENT
            )
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
        val result = database.child(FIREBASE_MAL_TOKEN_URL).get()
        return result
    }

    private fun getBaseUrl(): DatabaseReference {
        val year: Int = CalendarUtilities.Companion.determineDay()
        val season: String = CalendarUtilities.Companion.determineSeason()
        val reference: DatabaseReference = database.child(emailFormatted)
            .child(year.toString())
            .child(season)
        return reference
    }

    private fun getBaseUrl(year: Int, season: String): DatabaseReference {
        val reference: DatabaseReference = database.child(emailFormatted)
            .child(year.toString())
            .child(season)
        return reference
    }
}
package com.rickmark.seriesviewmanager.domain.interfaces.firebase

import com.google.firebase.auth.FirebaseUser

interface IFirebaseUserAuthenticator {

    fun createUser(email: String, password: String): Unit
    fun loginUser(email: String, password: String): Unit

    fun logoutUser(): Unit

    fun getCurrentUser(): FirebaseUser?
}
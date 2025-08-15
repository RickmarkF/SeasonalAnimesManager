package com.rickmark.seriesviewmanager.domain.interfaces.firebase

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser

interface IFirebaseUserAuthenticator {

    fun createUser(context: AppCompatActivity, email: String, password: String): Unit
    fun loginUser(context: AppCompatActivity, email: String, password: String): Unit

    fun logoutUser(): Unit

    fun getCurrentUser(): FirebaseUser?
}
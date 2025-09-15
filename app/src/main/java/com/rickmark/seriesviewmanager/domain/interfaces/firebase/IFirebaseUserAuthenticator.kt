package com.rickmark.seriesviewmanager.domain.interfaces.firebase

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface IFirebaseUserAuthenticator {

    fun createUser(email: String?, password: String, callback: (Boolean) -> Unit): Task<AuthResult?>?
    fun loginUser(email: String?, password: String, callback: (Boolean) -> Unit): Task<AuthResult?>?

    fun logoutUser(): Unit

    fun getCurrentUser(): FirebaseUser?
}
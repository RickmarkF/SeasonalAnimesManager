package com.rickmark.seriesviewmanager.data.firebase_connection.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFirebaseUserAuthenticator

class FirebaseUserAuthentication() : IFirebaseUserAuthenticator {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun createUser(email: String?, password: String, callback: (Boolean) -> Unit): Task<AuthResult?>? {
        var result: Task<AuthResult?>? = null
        if (email?.isNotEmpty() == true && password.isNotEmpty()) {
           result = auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task -> callback(true) }
                .addOnFailureListener { task -> callback(false) }
        }
        return result
    }

    override fun loginUser(email: String?, password: String, callback: (Boolean) -> Unit): Task<AuthResult?>? {
        var result: Task<AuthResult?>? = null
        if (email?.isNotEmpty() == true && password.isNotEmpty()) {
          result =  auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task -> callback(true) }
                .addOnFailureListener { task -> callback(false) }
        }
        return result
    }

    override fun logoutUser() {
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

}
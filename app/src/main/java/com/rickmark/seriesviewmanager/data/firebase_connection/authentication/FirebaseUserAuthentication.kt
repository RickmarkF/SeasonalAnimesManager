package com.rickmark.seriesviewmanager.data.firebase_connection.authentication

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFirebaseUserAuthenticator
import com.rickmark.seriesviewmanager.ui.seasonalAnime.ViewSeasonalAnimeActivity

class FirebaseUserAuthentication() : IFirebaseUserAuthenticator {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun createUser(context: AppCompatActivity, email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context) { task ->
                    this.addSuccesfullistener(context, "Succesfully registered")
                }
                .addOnFailureListener(context) { task ->
                    this.addFailedlistener(context, "Something went wrong during registration")
                }
        }

    }

    override fun loginUser(context: AppCompatActivity, email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context) { task ->
                    this.addSuccesfullistener(context, "Succesfully logged in")
                }
                .addOnFailureListener(context) { task ->
                    this.addFailedlistener(context, "Error during login")
                }
        }
    }

    private fun addSuccesfullistener(
        context: AppCompatActivity,
        message: String
    ): Unit {
        val sendIntent = Intent(context, ViewSeasonalAnimeActivity::class.java)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        context.startActivity(sendIntent)
        context.finish()
    }

    private fun addFailedlistener(
        context: AppCompatActivity,
        message: String
    ): Unit {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
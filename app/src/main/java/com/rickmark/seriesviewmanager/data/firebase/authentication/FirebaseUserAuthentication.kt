package com.rickmark.seriesviewmanager.data.firebase.authentication

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFirebaseUserAuthenticator
import com.rickmark.seriesviewmanager.ui.seasonalAnime.ViewSeasonalAnimeActivity

class FirebaseUserAuthentication() : IFirebaseUserAuthenticator {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun createUser(context: AppCompatActivity,email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context){ task ->
                    this.addSuccesfullistener(context,task)
                }
        }

    }

    override fun loginUser(context: AppCompatActivity,email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context){ task ->
                    this.addSuccesfullistener(context,task)
                }
        }
    }

    override fun logoutUser() {
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    private fun <TResult> addSuccesfullistener(context: AppCompatActivity, task: Task<TResult>): Unit {
        if (task.isSuccessful) {
            val sendIntent = Intent(context, ViewSeasonalAnimeActivity::class.java)
            Toast.makeText(context, "Login correcto", Toast.LENGTH_LONG).show()
            Log.d("Login", "correct login")
            context.startActivity(sendIntent)
            context.finish()
        }
    }


}
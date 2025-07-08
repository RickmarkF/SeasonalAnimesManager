package com.rickmark.seriesviewmanager.data.authentication

import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.rickmark.seriesviewmanager.domain.interfaces.IUserAuthenticator
import com.rickmark.seriesviewmanager.ui.LoginActivity
import com.rickmark.seriesviewmanager.ui.SearchAnimeActivity

class UserAuthentication(
    private val auth: FirebaseAuth,
    private val context: LoginActivity
): IUserAuthenticator {

    override fun createUser(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        val sendIntent = Intent(context, SearchAnimeActivity::class.java)
                        Log.d("Login", "signInWithEmail:success")
                        context.startActivity(sendIntent)
                        context.finish()
                    }
                }
        }

    }

    override fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        val sendIntent = Intent(context, SearchAnimeActivity::class.java)
                        Log.d("Login", "signInWithEmail:success")
                        context.startActivity(sendIntent)
                        context.finish()
                    }
                }
        }
    }


}
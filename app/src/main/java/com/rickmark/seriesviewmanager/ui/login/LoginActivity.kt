package com.rickmark.seriesviewmanager.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.firebase.authentication.FirebaseUserAuthentication
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFirebaseUserAuthenticator
import com.rickmark.seriesviewmanager.ui.seasonalAnime.ViewSeasonalAnimeActivity

class LoginActivity : AppCompatActivity(R.layout.login_activity) {

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonCreateAccount: Button
    private lateinit var buttonLogin: Button
    private lateinit var userAuthenticator: IFirebaseUserAuthenticator

    fun prepareWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        return insets
    }

    public override fun onStart() {
        super.onStart()
        userAuthenticator = FirebaseUserAuthentication()
        val currentUser = userAuthenticator.getCurrentUser()
        if (currentUser != null) {
            val sendIntent = Intent(this, ViewSeasonalAnimeActivity::class.java)
            startActivity(sendIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.login),
            this::prepareWindowInsets
        )

        editTextEmailAddress = findViewById(R.id.login_email_edit_text)
        editTextPassword = findViewById(R.id.login_password_edit_text)
        buttonCreateAccount = findViewById(R.id.login_create_account_button)
        buttonLogin = findViewById(R.id.login_account_button)


        buttonCreateAccount.setOnClickListener() {
            authenticateUser(userAuthenticator::createUser)
        }
        buttonLogin.setOnClickListener() {
            authenticateUser(userAuthenticator::loginUser)
        }
    }

    private inline fun authenticateUser(autenticate: (context: AppCompatActivity,email: String, password: String) -> Unit): Unit {
        val email: String = editTextEmailAddress.text.toString()
        val password: String = editTextPassword.text.toString()
        autenticate(this,email, password)
    }
}
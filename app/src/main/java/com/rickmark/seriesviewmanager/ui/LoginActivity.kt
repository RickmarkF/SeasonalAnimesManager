package com.rickmark.seriesviewmanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.authentication.UserAuthentication
import com.rickmark.seriesviewmanager.domain.interfaces.IUserAuthenticator
import com.rickmark.seriesviewmanager.ui.seasonalAnime.ViewSeasonalAnimeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonCreateAccount: Button
    private lateinit var buttonLogin: Button

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var userAuthenticator: IUserAuthenticator

    fun prepareWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        return insets
    }

    public override fun onStart() {
        super.onStart()
        userAuthenticator = UserAuthentication(auth, this)
        //MyHTTPServer(8080).also { it.start() }
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val sendIntent = Intent(this, ViewSeasonalAnimeActivity::class.java)
            startActivity(sendIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)


        editTextEmailAddress = findViewById(R.id.editTextEmailAddress)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonCreateAccount = findViewById(R.id.createAccount)
        buttonLogin = findViewById(R.id.loginButton)


        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.login),
            this::prepareWindowInsets
        )

        buttonCreateAccount.setOnClickListener(this::register)
        buttonLogin.setOnClickListener(this::login)
    }

    private fun register(view: View): Unit {

        val email: String = editTextEmailAddress.text.toString()
        val password: String = editTextPassword.text.toString()

        userAuthenticator.createUser(email, password)
    }

    private fun login(view: View): Unit {

        val email: String = editTextEmailAddress.text.toString()
        val password: String = editTextPassword.text.toString()

        userAuthenticator.loginUser(email, password)

    }
}

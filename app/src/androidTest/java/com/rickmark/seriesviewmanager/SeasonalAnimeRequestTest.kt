package com.rickmark.seriesviewmanager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.rickmark.seriesviewmanager.data.firebase_connection.repository.FirebaseRepository
import com.rickmark.seriesviewmanager.data.mal_request.MalRequest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SeasonalAnimeRequestTest {

    private lateinit var request : MalRequest

    @Before
    fun setup() {
        val email = generateTestEmail()
        val password = "12345678"
        val authResultTask = FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
        Tasks.await(authResultTask)

        val a = FirebaseRepository()
        val dataSnapshot: DataSnapshot? = Tasks.await(a.getMalToken())
        val token = dataSnapshot?.value.toString()


        val context = ApplicationProvider.getApplicationContext<Context>()
        val resources = context.resources

        request = MalRequest(token,resources)
    }

    fun generateTestEmail(): String {
        val timestamp = System.currentTimeMillis()
        return "testUser_$timestamp@gmail.com"
    }

    @After
    fun end(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Tasks.await(user.delete())
        }
    }


    @Test
    fun testLoadSeasonalAnimes() = runBlocking {

        val result = request.getSeasonalAnime("summer",2025)
        assert(result != null && result.isNotEmpty())
        assert(result?.size == 266)
    }

    @Test
    fun testLoadAnimeDetails() = runBlocking {
        val result = request.getAnimeDetails(1)
        assert(result != null)
        assert(result?.title == "Cowboy Bebop")
    }
}
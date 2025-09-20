package com.rickmark.seriesviewmanager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.rickmark.seriesviewmanager.data.firebase_connection.repository.FirebaseInfoRepository
import com.rickmark.seriesviewmanager.data.mal_request.MalRequestCreation
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SeasonalAnimeRequestTest {

    private lateinit var request: MalRequestCreation

    @Before
    fun setup() {
        val email = generateTestEmail()
        val password = "12345678"
        val authResultTask = FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
        Tasks.await(authResultTask)

        val a = FirebaseInfoRepository()
        val dataSnapshot: DataSnapshot? = Tasks.await(a.getMalToken())
        val token = dataSnapshot?.value.toString()


        val context = ApplicationProvider.getApplicationContext<Context>()
        val resources = context.resources

        request = MalRequestCreation(token, resources)
    }

    fun generateTestEmail(): String {
        val timestamp = System.currentTimeMillis()
        return "testUser_$timestamp@gmail.com"
    }

    @After
    fun end() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Tasks.await(user.delete())
        }
    }


    @Test
    fun testLoadSeasonalAnimes() = runBlocking {

        val result = request.getSeasonalAnime("summer", 2025)
        assert(result != null && result.isNotEmpty())
    }

    @Test
    fun testLoadAnimeDetails() = runBlocking {
        val result = request.getAnimeDetails(1)
        assert(result != null)
        assert(result?.title == "Cowboy Bebop")
    }
}
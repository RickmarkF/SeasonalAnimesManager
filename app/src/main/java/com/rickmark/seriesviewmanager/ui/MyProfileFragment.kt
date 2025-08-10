package com.rickmark.seriesviewmanager.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.rickmark.seriesviewmanager.R
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rickmark.seriesviewmanager.data.CalendarUtilities
import com.rickmark.seriesviewmanager.data.FirebaseRepository
import com.rickmark.seriesviewmanager.domain.interfaces.IFarebaseRespository
import com.rickmark.seriesviewmanager.ui.reciclerViews.SeasonalAnimeRecyclerAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    val repository: IFarebaseRespository = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName: TextView = view.findViewById<TextView>(R.id.profile_user_name)
        val seasonSpinner: Spinner = view.findViewById<Spinner>(R.id.season_anime_spinner)
        val yearSpinner: Spinner = view.findViewById<Spinner>(R.id.year_anime_spinner)
        val anime_whistliss: RecyclerView = view.findViewById<RecyclerView>(R.id.season_anime_list)

        updateUserName(userName)
        updateSpinner(seasonSpinner, mutableListOf("winter", "spring", "summer", "fall"))

        val actualYear: Int = CalendarUtilities.getYear()
        val yearsList: MutableList<String> = mutableListOf()
        for (i in 2025..actualYear) {
            yearsList.add(i.toString())
        }
        updateSpinner(yearSpinner, yearsList)

        repository.readFromFirebase().addOnSuccessListener { dataSnapshot ->
            val result : Map<String, Long> = dataSnapshot?.value as Map<String, Long>
            result.forEach { (key, value) ->
                Log.d("Firebase", "Key: $key, Value: $value")
            }

       }
    }

    fun updateSpinner(spinner: Spinner,list: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }

    fun updateUserName(userName: TextView) {
        userName.text = "Hola ${FirebaseAuth.getInstance().currentUser?.email}" +
                "Se va a mostrar a continuación los animes que has agregado a tu lista para ver"
    }
}
package com.rickmark.seriesviewmanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.CalendarUtilities
import com.rickmark.seriesviewmanager.data.FirebaseRepository
import com.rickmark.seriesviewmanager.data.SeasonalAnimeViewModel
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.Data
import com.rickmark.seriesviewmanager.ui.reciclerViews.MyProfileRecyclerAdapter
import com.rickmark.seriesviewmanager.ui.seasonalAnime.ViewSeasonalAnimeActivity
import kotlinx.coroutines.launch
import kotlin.getValue

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    val repository: IFarebaseRespository = FirebaseRepository()

    private val seasonalAnimeViewModel: SeasonalAnimeViewModel by activityViewModels()
    val manager: IAnimeManager = AnimeManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName: TextView = view.findViewById<TextView>(R.id.profile_user_name)
        val seasonSpinner: Spinner = view.findViewById<Spinner>(R.id.season_anime_spinner)
        val yearSpinner: Spinner = view.findViewById<Spinner>(R.id.year_anime_spinner)
        val anime_whistliss: RecyclerView = view.findViewById<RecyclerView>(R.id.season_anime_list)
        val showAnimeButton: Button = view.findViewById<Button>(R.id.show_anime_button)
        val singOutButton: Button = view.findViewById<Button>(R.id.sing_out_button)

        singOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val sendIntent = Intent(activity, LoginActivity::class.java)
            startActivity(sendIntent)
        }

        updateUserName(userName)
        val seasons: Array<String> = resources.getStringArray(R.array.seasons)
        updateSpinner(seasonSpinner, seasons.toMutableList())

        val actualYear: Int = CalendarUtilities.getYear()
        val yearsList: MutableList<String> = mutableListOf()
        for (i in 2025..actualYear) {
            yearsList.add(i.toString())
        }
        updateSpinner(yearSpinner, yearsList)

        showAnimeButton.setOnClickListener {
            showAnime(anime_whistliss, seasonSpinner, yearSpinner)
        }


    }

    private fun showAnime(
        anime_whistliss: RecyclerView,
        seasonSpinner: Spinner,
        yearSpinner: Spinner
    ) {

        val season: String = seasonSpinner.selectedItem.toString()
        val year: String = yearSpinner.selectedItem.toString()

        repository.readFromFirebase(year.toInt(), season).addOnSuccessListener { dataSnapshot ->
            lifecycleScope.launch {
                if (dataSnapshot?.value == null){
                    return@launch
                }

                val result: Map<String, Long> = dataSnapshot?.value as Map<String, Long>
                val animes: MutableList<Data> = mutableListOf()
                result.forEach { (key, value) ->
                    val data: Data? = seasonalAnimeViewModel.seasonalAnimes.value?.firstOrNull() {
                        it.node.id == value
                    }
                    animes.add(data!!)
                }

                anime_whistliss.also {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.adapter = MyProfileRecyclerAdapter(animes, requireContext())

                }
            }
        }
    }

    fun updateSpinner(spinner: Spinner, list: MutableList<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }

    fun updateUserName(userName: TextView) {
        userName.text = "Hola ${FirebaseAuth.getInstance().currentUser?.email}" +
                "Se va a mostrar a continuación los animes que has agregado a tu lista para ver"
    }
}


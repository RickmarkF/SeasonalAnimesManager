package com.rickmark.seriesviewmanager.ui.my_profile

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.rickmark.seriesviewmanager.data.firebase.authentication.FirebaseUserAuthentication
import com.rickmark.seriesviewmanager.data.firebase.repository.FirebaseRepository
import com.rickmark.seriesviewmanager.data.utilities.CalendarUtilities
import com.rickmark.seriesviewmanager.data.utilities.UiUtilities.Companion.updateSpinner
import com.rickmark.seriesviewmanager.data.view_models.SeasonalAnimeViewModel
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFirebaseUserAuthenticator
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_list.Data
import com.rickmark.seriesviewmanager.ui.login.LoginActivity
import kotlinx.coroutines.launch

class MyProfileFragment : Fragment(R.layout.my_profile_fragment) {

    val repository: IFarebaseRespository = FirebaseRepository()
    val auth: IFirebaseUserAuthenticator = FirebaseUserAuthentication()

    private val seasonalAnimeViewModel: SeasonalAnimeViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName: TextView = view.findViewById(R.id.my_profile_user_name)
        userName.also {
            updateUserName(it)
        }


        val seasonSpinner: Spinner =
            view.findViewById(R.id.my_profile_user_anime_season_spinner)
        seasonSpinner.also {
            val seasons: Array<String> = resources.getStringArray(R.array.seasons)
            updateSpinner(context, seasonSpinner, seasons.toMutableList())
        }


        val yearSpinner: Spinner = view.findViewById(R.id.my_profile_anime_year_spinner)
        yearSpinner.also {
            val actualYear: Int = CalendarUtilities.Companion.getYear()
            val yearsList: MutableList<String> = mutableListOf()
            for (i in 2025..actualYear) {
                yearsList.add(i.toString())
            }
            updateSpinner(context, yearSpinner, yearsList)

        }


        val singOutButton: Button = view.findViewById(R.id.my_profile_sing_out_button)
        singOutButton.also {
            it.setOnClickListener {
                auth.logoutUser()
                val sendIntent = Intent(activity, LoginActivity::class.java)
                startActivity(sendIntent)
                activity?.finish()
            }
        }


        val anime_whistliss: RecyclerView =
            view.findViewById(R.id.my_profile_anime_season_recycler_view)
        val showAnimeButton: Button =
            view.findViewById(R.id.my_profile_show_animes_saved_button)

        showAnimeButton.also {
            it.setOnClickListener {
                showAnime(anime_whistliss, seasonSpinner, yearSpinner)
            }
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
                if (dataSnapshot?.value == null) {
                    return@launch
                }

                val result: Map<String, Long> = dataSnapshot.value as Map<String, Long>
                val anime: MutableList<Data> = mutableListOf()
                result.forEach { (key, value) ->
                    val data: Data? = seasonalAnimeViewModel.seasonalAnimes.value?.firstOrNull() {
                        it.node.id == value
                    }
                    anime.add(data!!)
                }

                anime_whistliss.also {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.adapter = MyProfileRecyclerAdapter(anime, requireContext())

                }
            }
        }
    }


    fun updateUserName(userName: TextView) {
        userName.text = "Hola ${FirebaseAuth.getInstance().currentUser?.email}" +
                "Se va a mostrar a continuación los animes que has agregado a tu lista para ver"
    }
}
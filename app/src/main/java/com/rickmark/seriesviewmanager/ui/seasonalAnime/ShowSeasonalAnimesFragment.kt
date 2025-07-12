package com.rickmark.seriesviewmanager.ui.seasonalAnime


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.fragment
import com.rickmark.seriesviewmanager.R

class ShowSeasonalAnimesFragment : Fragment(R.layout.fragment_show_seasonal_animes) {

     data class Plant(val id: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Retrieve the NavController.

        val navHostFragment = findNavController()
        val navController = navHostFragment.navigate("v")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button3)
        button.setOnClickListener {
            val navHostFragment = findNavController()
            navHostFragment.popBackStack()
        }
    }
}
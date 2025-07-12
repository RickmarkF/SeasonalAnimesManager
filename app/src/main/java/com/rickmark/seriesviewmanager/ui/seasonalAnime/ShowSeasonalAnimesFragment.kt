package com.rickmark.seriesviewmanager.ui.seasonalAnime


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.domain.constants.NavegationRutes

class ShowSeasonalAnimesFragment : Fragment(R.layout.fragment_show_seasonal_animes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button3)
        button.setOnClickListener {
            val navHostFragment = findNavController()
            val navController = navHostFragment.navigate(NavegationRutes.FRAGMENT_ANIME_DETAIL)
        }
    }
}
package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rickmark.seriesviewmanager.R

class DetailSeasonalAnimeFragment : Fragment(R.layout.fragment_detail_seasonal_anime) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Obtener el argumento de la ruta
        val encodedData = arguments?.getInt("anime_id")
        val nav = findNavController()


    }



}
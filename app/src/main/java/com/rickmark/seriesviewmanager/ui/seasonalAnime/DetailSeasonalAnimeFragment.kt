package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rickmark.seriesviewmanager.R

class DetailSeasonalAnimeFragment : Fragment(R.layout.fragment_detail_seasonal_anime) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            val navHostFragment = findNavController()
            navHostFragment.popBackStack()
        }
    }

}
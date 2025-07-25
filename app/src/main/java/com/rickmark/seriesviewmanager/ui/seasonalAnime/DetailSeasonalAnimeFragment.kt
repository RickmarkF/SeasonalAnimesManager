package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails

class DetailSeasonalAnimeFragment : Fragment(R.layout.fragment_detail_seasonal_anime) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animeId: Int? = arguments?.getInt("anime_id")
        val nav = findNavController()

        val manager: IAnimeManager = AnimeManager()
        val data: AnimeDetails? = manager.getAnimeDetails(animeId)


    }


}
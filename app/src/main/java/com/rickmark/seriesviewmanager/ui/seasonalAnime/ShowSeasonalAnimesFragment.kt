package com.rickmark.seriesviewmanager.ui.seasonalAnime


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.SeasonalAnimeViewModel
import com.rickmark.seriesviewmanager.domain.models.Data
import com.rickmark.seriesviewmanager.ui.reciclerViews.SeasonalAnimeRecyclerAdapter

class ShowSeasonalAnimesFragment : Fragment(R.layout.fragment_show_seasonal_animes) {

    private val seasonalAnimeViewModel: SeasonalAnimeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seasonalAnimeList: List<Data>? = seasonalAnimeViewModel.loadSeasonalAnimesIfNeeded()

        val temporada: TextView = view.findViewById(R.id.temporada_actual)
        val seasonalAnimeRecyclerView: RecyclerView = view.findViewById(R.id.lista_temporada)


        if (seasonalAnimeList != null) {
            temporada.text =
                "Temporada: ${seasonalAnimeViewModel.getSeason()}\n Año:${seasonalAnimeViewModel.getYear()}"
            temporada.textSize = 30f
            seasonalAnimeRecyclerView.also {
                it.layoutManager = GridLayoutManager(view.context, 2)
                it.adapter = SeasonalAnimeRecyclerAdapter(seasonalAnimeList, view.context)
            }

        }
    }
}
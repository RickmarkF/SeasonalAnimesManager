package com.rickmark.seriesviewmanager.ui.seasonalAnime


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.Data
import com.rickmark.seriesviewmanager.ui.reciclerViews.SeasonalAnimeRecyclerAdapter

class ShowSeasonalAnimesFragment : Fragment(R.layout.fragment_show_seasonal_animes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager: IAnimeManager = AnimeManager()
        val lista_temporada: RecyclerView = view.findViewById(R.id.lista_temporada)
        lista_temporada.layoutManager = GridLayoutManager(view.context, 2)
        val seasonalAnimeList: List<Data>? = manager.getSeasonalAnime()
        if (seasonalAnimeList != null) {
            lista_temporada.adapter = SeasonalAnimeRecyclerAdapter(seasonalAnimeList, view.context)
        }
    }
}
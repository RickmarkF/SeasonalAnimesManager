package com.rickmark.seriesviewmanager.ui.seasonalAnime


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.constants.NavegationRutes
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.BaseData
import com.rickmark.seriesviewmanager.ui.reciclerViews.SeasonalAnimeRecyclerAdapter

class ShowSeasonalAnimesFragment : Fragment(R.layout.fragment_show_seasonal_animes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager: IAnimeManager = AnimeManager()
        val lista_temporada: RecyclerView = view.findViewById(R.id.lista_temporada)
        lista_temporada.layoutManager = GridLayoutManager(view.context,2)
        val basedata: BaseData? = manager.getSeasonalAnime()
        if ( basedata!= null){
            lista_temporada.adapter = SeasonalAnimeRecyclerAdapter(basedata.data,view.context)
        }
    }
}
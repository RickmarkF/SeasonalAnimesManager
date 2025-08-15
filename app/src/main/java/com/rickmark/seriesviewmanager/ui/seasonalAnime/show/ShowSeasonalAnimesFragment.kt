package com.rickmark.seriesviewmanager.ui.seasonalAnime.show


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.view_models.SeasonalAnimeViewModel

class ShowSeasonalAnimesFragment : Fragment(R.layout.show_seasonal_animes_fragment) {

    private val seasonalAnimeViewModel: SeasonalAnimeViewModel by activityViewModels() { SeasonalAnimeViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seasonTextView: TextView =
            view.findViewById(R.id.seasonal_anime_current_season_text_view)
        val animesRecyclerView: RecyclerView =
            view.findViewById(R.id.seasonal_animes_recycler_view)

        seasonalAnimeViewModel.seasonalAnimes.observe(viewLifecycleOwner) { seasonalAnimeList ->
            if (seasonalAnimeList != null) {
                seasonTextView.text =
                    "Current Season: ${seasonalAnimeViewModel.season}\n " +
                            "Current year:${seasonalAnimeViewModel.year}"
                seasonTextView.textSize = 30f
                animesRecyclerView.also {
                    it.layoutManager = GridLayoutManager(view.context, 2)
                    it.adapter = ShowSeasonalAnimesAdapter(
                        seasonalAnimeList,
                        view.context
                    )
                }

            }

        }


    }
}
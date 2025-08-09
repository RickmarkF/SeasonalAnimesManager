package com.rickmark.seriesviewmanager.data

import androidx.lifecycle.ViewModel
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.Data
import java.util.Calendar

class SeasonalAnimeViewModel : ViewModel() {
    private val manager: IAnimeManager = AnimeManager()

    private var seasonalAnimeList: List<Data>? = null
    private var season: String = CalendarUtilities.getSeason()
    private var year: Int = CalendarUtilities.getYear()


    fun getSeason(): String = this.season
    fun getYear(): Int = this.year


    fun loadSeasonalAnimesIfNeeded(): List<Data>? {

        if (seasonalAnimeList != null) {
            return seasonalAnimeList
        }

        seasonalAnimeList = manager.getSeasonalAnime(season, year)
        return seasonalAnimeList
    }
}
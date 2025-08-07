package com.rickmark.seriesviewmanager.data

import androidx.lifecycle.ViewModel
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.Data
import java.util.Calendar

class SeasonalAnimeViewModel : ViewModel() {
    private val manager: IAnimeManager = AnimeManager()

    private var seasonalAnimeList: List<Data>? = null
    private lateinit var season: String
    private var year: Int = 0


    fun getSeason(): String = this.season
    fun getYear(): Int = this.year


    fun loadSeasonalAnimesIfNeeded(): List<Data>? {

        if (seasonalAnimeList != null) {
            return seasonalAnimeList
        }

        setCurrentlySeason()
        seasonalAnimeList = manager.getSeasonalAnime(season, year)
        return seasonalAnimeList
    }

    private fun setCurrentlySeason(): Unit {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        this.year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        when (month) {
            Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH -> {
                this.season = "winter"
            }

            Calendar.APRIL, Calendar.MAY, Calendar.JUNE -> {
                this.season = "spring"
            }

            Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER -> {
                this.season = "summer"
            }

            Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER -> {
                this.season = "fall"
            }
        }
    }


}
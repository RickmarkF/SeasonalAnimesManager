package com.rickmark.seriesviewmanager.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.Data
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SeasonalAnimeViewModel : ViewModel() {
    private val manager: IAnimeManager = AnimeManager()

    private val _seasonalAnimes = MutableLiveData<List<Data>>()
    val seasonalAnimes: LiveData<List<Data>> get() = _seasonalAnimes


    val season: String
        get() = CalendarUtilities.getSeason()
    val year: Int
        get() = CalendarUtilities.getYear()


    suspend fun loadSeasonalAnimesIfNeeded(): Unit {
            if (_seasonalAnimes.value.isNullOrEmpty()) {
                val data: List<Data>? = manager.getSeasonalAnime(season, year)
                if (data != null) {
                    _seasonalAnimes.value = data.sortedBy { it.node.title }
                }
            }
    }


}
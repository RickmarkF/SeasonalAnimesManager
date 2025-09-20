package com.rickmark.seriesviewmanager.data.view_models

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.rickmark.seriesviewmanager.data.app_utilities.CalendarUtilities
import com.rickmark.seriesviewmanager.data.mal_request.MalRequestCreation
import com.rickmark.seriesviewmanager.domain.interfaces.mal.IMalRequestManager
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_list.Data

class SeasonalAnimeViewModel(private val resources: Resources) : ViewModel() {
    private lateinit var manager: IMalRequestManager

    private val _seasonalAnimes = MutableLiveData<List<Data>>()
    val seasonalAnimes: LiveData<List<Data>> get() = _seasonalAnimes


    val season: String
        get() = CalendarUtilities.determineSeason()
    val year: Int
        get() = CalendarUtilities.determineDay()

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                return SeasonalAnimeViewModel(
                    application.resources
                ) as T
            }
        }
    }

    suspend fun loadSeasonalAnimesIfNeeded(token: String) {
        if (_seasonalAnimes.value.isNullOrEmpty()) {
            manager = MalRequestCreation(resources = resources, token = token)
            val seasonalAnimeList: List<Data>? = manager.getSeasonalAnime(season, year)
            if (seasonalAnimeList != null) {
                _seasonalAnimes.value = seasonalAnimeList.sortedBy { it.node.title }
            }
        }
    }


}
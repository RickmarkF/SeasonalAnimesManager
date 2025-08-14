package com.rickmark.seriesviewmanager.data.view_models

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickmark.seriesviewmanager.data.request.MalRequest
import com.rickmark.seriesviewmanager.data.utilities.CalendarUtilities
import com.rickmark.seriesviewmanager.domain.interfaces.mal.IMalRequestManager
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_list.Data

class SeasonalAnimeViewModel() : ViewModel() {
    private lateinit var manager: IMalRequestManager

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

    fun loadRequest(token: String, resources: Resources) {
        manager = MalRequest(resources = resources, token = token)

    }


}
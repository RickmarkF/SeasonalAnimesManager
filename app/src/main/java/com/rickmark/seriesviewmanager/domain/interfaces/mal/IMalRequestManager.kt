package com.rickmark.seriesviewmanager.domain.interfaces.mal

import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_details.AnimeSeasonDetails
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_list.Data

interface IMalRequestManager {

    suspend fun getSeasonalAnime(
        season: String, year: Int
    ): List<Data>?

    suspend fun getAnimeDetails(id: Int?): AnimeSeasonDetails?
}
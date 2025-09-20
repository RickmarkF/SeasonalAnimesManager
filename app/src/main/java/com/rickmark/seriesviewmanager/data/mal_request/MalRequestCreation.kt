package com.rickmark.seriesviewmanager.data.mal_request

import android.content.res.Resources
import com.rickmark.seriesviewmanager.domain.interfaces.mal.IMalRequestManager
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_details.AnimeSeasonDetails
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_list.AnimeSeasonList
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_list.Data
import io.ktor.client.call.body

class MalRequestCreation() : IMalRequestManager {
    private lateinit var requestPreparartion: MalRequestPreparation

    constructor(token: String, resources: Resources) : this() {
        requestPreparartion = MalRequestPreparation(resources, token)
    }

    override suspend fun getSeasonalAnime(season: String, year: Int): List<Data>? {

        val seasonalAnimes: AnimeSeasonList = requestPreparartion
            .setUrl("anime/season/${year}/${season}")
            .setHttpRequest("limit", 500)
            .buildGetHttpRequest()
            .body()

        return seasonalAnimes.data
    }

    override suspend fun getAnimeDetails(id: Int?): AnimeSeasonDetails? {

        val details: AnimeSeasonDetails = requestPreparartion
            .setUrl("anime/${id}")
            .setHttpRequest(
                "fields", "title," +
                        "main_picture," +
                        "alternative_titles," +
                        "start_date," +
                        "end_date," +
                        "synopsis," +
                        "media_type," +
                        "status," +
                        "genres," +
                        "num_episodes," +
                        "start_season," +
                        "source," +
                        "average_episode_duration," +
                        "pictures," +
                        "related_anime," +
                        "related_manga," +
                        "recommendations," +
                        "studios"
            )
            .buildGetHttpRequest()
            .body()

        return details
    }
}
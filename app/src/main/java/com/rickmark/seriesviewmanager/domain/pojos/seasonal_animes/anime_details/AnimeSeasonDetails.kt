package com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_details

import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.commons.Picture
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

@Serializable
data class AnimeSeasonDetails(
    val id: Long,
    val title: String,

    @SerialName("main_picture")
    val mainPicture: Picture,

    @SerialName("alternative_titles")
    val alternativeTitles: AlternativeTitles,

    @SerialName("start_date")
    val startDate: String,

    val synopsis: String,

    @SerialName("media_type")
    val mediaType: String,

    val status: String,
    val genres: List<Genre>,

    @SerialName("num_episodes")
    val numEpisodes: Long,

    @SerialName("start_season")
    val startSeason: StartSeason,

    val source: String,

    @SerialName("average_episode_duration")
    val averageEpisodeDuration: Long,

    val pictures: List<Picture>,

    @SerialName("related_anime")
    val relatedAnime: List<RelatedAnime>,

    @SerialName("related_manga")
    val relatedManga: JsonArray,

    val recommendations: List<Recommendation>,
    val studios: List<Genre>
)
package com.rickmark.seriesviewmanager.domain.interfaces

interface ISreriesManage {

    fun searchAnime(animeName: String): Unit
    fun deleteAnime(animeName: String): Unit
    fun addAnime(animeName: String): Unit
    fun updateAnime(animeName: String): Unit
    fun getAnimeList(): Unit

}
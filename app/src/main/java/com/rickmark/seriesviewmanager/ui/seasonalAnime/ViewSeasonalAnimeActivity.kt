package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager

class ViewSeasonalAnimeActivity : AppCompatActivity(R.layout.activity_view_seasonal_anime) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = "view_seasonal_anime",
        ) {
            fragment<ShowSeasonalAnimesFragment>("view_seasonal_anime"){

            }
            fragment<DetailSeasonalAnimeFragment>("v")
        }

        var manager: IAnimeManager = AnimeManager()
        manager.getSeasonalAnime()

    }

}
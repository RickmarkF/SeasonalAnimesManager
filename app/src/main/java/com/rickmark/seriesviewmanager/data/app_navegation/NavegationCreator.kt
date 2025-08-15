package com.rickmark.seriesviewmanager.data.app_navegation

import android.content.res.Resources
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.ui.my_profile.MyProfileFragment
import com.rickmark.seriesviewmanager.ui.seasonalAnime.details.DetailSeasonalAnimeFragment
import com.rickmark.seriesviewmanager.ui.seasonalAnime.show.ShowSeasonalAnimesFragment

class NavegationCreator() {


    private lateinit var navController: NavController

    private lateinit var navegation_seasonal_animes: String
    private lateinit var navegation_detail_animes: String
    private lateinit var navegation_profile: String


    constructor(
        navController: NavController,
        resources: Resources
    ) : this() {
        this.navController = navController
        navegation_seasonal_animes = resources.getString(R.string.navegation_seasonal_animes)
        navegation_detail_animes = resources.getString(R.string.navegation_detail_animes)
        navegation_profile = resources.getString(R.string.navegation_profile)
    }

    fun createAppNavGraph(): NavGraph {
        return navController.createGraph(
            startDestination = navegation_seasonal_animes,
            builder = prepareAppGraphBuilder()
        )
    }

    private fun prepareAppGraphBuilder(): NavGraphBuilder.() -> Unit = {
        fragment<ShowSeasonalAnimesFragment>(navegation_seasonal_animes)
        fragment<DetailSeasonalAnimeFragment>("$navegation_detail_animes/{anime_id}") {
            label = "Anime detail"
            argument("anime_id") {
                type = NavType.IntType
            }
        }

        fragment<MyProfileFragment>(navegation_profile) {
            label = "My profile"
        }
    }
}
package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.domain.constants.NavegationRutes

class ViewSeasonalAnimeActivity : AppCompatActivity(R.layout.activity_view_seasonal_anime) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = NavegationRutes.FRAGMENT_SEASONAL_ANIMES,
        ) {
            fragment<ShowSeasonalAnimesFragment>(NavegationRutes.FRAGMENT_SEASONAL_ANIMES) {
                label = "Animes de la temporada"
            }
            fragment<DetailSeasonalAnimeFragment>("${NavegationRutes.FRAGMENT_ANIME_DETAIL}/{anime_id}") {
                label = "Detalles del anime"
                argument("anime_id") {
                    type = NavType.IntType
                }
            }
        }

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)

        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_app_bar, menu)
        return true
    }


}
package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isEmpty
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.SupportActionBarViewModel
import com.rickmark.seriesviewmanager.domain.constants.NavegationRutes
import com.rickmark.seriesviewmanager.ui.MyProfileFragment

class ViewSeasonalAnimeActivity : AppCompatActivity(R.layout.activity_view_seasonal_anime) {

    private val actionBarViewModel: SupportActionBarViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = NavegationRutes.FRAGMENT_SEASONAL_ANIMES,
        ) {
            fragment<ShowSeasonalAnimesFragment>(NavegationRutes.FRAGMENT_SEASONAL_ANIMES)
            fragment<DetailSeasonalAnimeFragment>("${NavegationRutes.FRAGMENT_ANIME_DETAIL}/{anime_id}") {
                label = "Detalles anime"
                argument("anime_id") {
                    type = NavType.IntType
                }
            }

            fragment<MyProfileFragment>(NavegationRutes.FRAGMENT_PROFILE) {
                label = "Perfil"
            }
        }

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        actionBarViewModel.setActionBar(supportActionBar)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.seasonalAnime -> {
                    //TODO: Cargar el anime en firebase
                    true
                }

                else -> false
            }
        }

        val topLevelIds: List<Int> = navController.graph.filter {
            it.route == NavegationRutes.FRAGMENT_PROFILE ||
                    it.route == NavegationRutes.FRAGMENT_SEASONAL_ANIMES
        }.map {
            it.id
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.route) {
                NavegationRutes.FRAGMENT_SEASONAL_ANIMES -> {
                    supportActionBar?.hide()
                    if (toolbar.menu.isEmpty()) {
                        toolbar.inflateMenu(R.menu.top_app_bar)
                    }

                }

                else -> {
                    supportActionBar?.show()
                }
            }
        }

        val appBarConfiguration =
            AppBarConfiguration.Builder(topLevelDestinationIds = topLevelIds.toSet())
                .build()
        toolbar.setupWithNavController(navController, appBarConfiguration)


        val navBottom: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navBottom.menu.clear()
        navBottom.inflateMenu(R.menu.navegation)
        navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.seasonal_animes -> {
                    navController.navigate(NavegationRutes.FRAGMENT_SEASONAL_ANIMES)
                    supportActionBar?.hide()
                    true
                }

                R.id.profile -> {
                    navController.navigate(NavegationRutes.FRAGMENT_PROFILE)
                    toolbar.menu.clear()
                    true
                }

                else -> false
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_app_bar, menu)
        supportActionBar?.hide()
        return true
    }

}
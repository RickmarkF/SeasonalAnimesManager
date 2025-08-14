package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.view_models.SeasonalAnimeViewModel
import com.rickmark.seriesviewmanager.data.view_models.SupportActionBarViewModel
import com.rickmark.seriesviewmanager.ui.my_profile.MyProfileFragment
import com.rickmark.seriesviewmanager.ui.seasonalAnime.details.DetailSeasonalAnimeFragment
import com.rickmark.seriesviewmanager.ui.seasonalAnime.show.ShowSeasonalAnimesFragment
import kotlinx.coroutines.launch

class ViewSeasonalAnimeActivity : AppCompatActivity(R.layout.show_seasonal_animes_activity) {

    private val actionBarViewModel: SupportActionBarViewModel by viewModels()
    private val seasonalAnimeViewModel: SeasonalAnimeViewModel by viewModels()

    private lateinit var navegation_seasonal_animes: String
    private lateinit var navegation_detail_animes: String
    private lateinit var navegation_profile: String

    private lateinit var toolbar: Toolbar
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navBottom: BottomNavigationView


    override fun onStart() {
        super.onStart()
        navegation_seasonal_animes = getString(R.string.navegation_seasonal_animes)
        navegation_detail_animes = getString(R.string.navegation_detail_animes)
        navegation_profile = getString(R.string.navegation_profile)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.seasonal_anime_nav_host)
                    as NavHostFragment

        toolbar = findViewById(R.id.seasonal_anime_toolbar)

        navBottom = findViewById(R.id.app_bottom_nav)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch {
            seasonalAnimeViewModel.also {
                it.loadResources(resources)
                it.loadSeasonalAnimesIfNeeded()
            }

            prepareSeasonalAnimesNavegation()
            prepareNavButton()
        }

    }


    private fun prepareSeasonalAnimesNavegation(): Unit {
        val navController: NavController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = navegation_seasonal_animes,
        ) {
            fragment<ShowSeasonalAnimesFragment>(navegation_seasonal_animes)
            fragment<DetailSeasonalAnimeFragment>("$navegation_detail_animes/{anime_id}") {
                label = "Detalles anime"
                argument("anime_id") {
                    type = NavType.IntType
                }
            }

            fragment<MyProfileFragment>(navegation_profile) {
                label = "Perfil"
            }
        }

        setSupportActionBar(toolbar)
        actionBarViewModel.setToolbar(toolbar)
        actionBarViewModel.setActionBar(supportActionBar)

        val topLevelIds: List<Int> = navController.graph.filter {
            it.route == navegation_profile ||
                    it.route == navegation_seasonal_animes
        }.map {
            it.id
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.route) {
                navegation_seasonal_animes -> {
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
            AppBarConfiguration.Builder(topLevelDestinationIds = topLevelIds.toSet()).build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }


    private fun prepareNavButton() {
        val navController: NavController = navHostFragment.navController
        navBottom.menu.clear()
        navBottom.inflateMenu(R.menu.menus_navegation)
        navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_seasonal_animes -> {
                    navController.navigate(navegation_seasonal_animes)
                    supportActionBar?.hide()
                    true
                }

                R.id.nav_my_profile -> {
                    navController.navigate(navegation_profile)
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
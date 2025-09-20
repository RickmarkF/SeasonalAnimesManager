package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.app_navegation.NavegationCreator
import com.rickmark.seriesviewmanager.data.app_navegation.NavigationManager
import com.rickmark.seriesviewmanager.data.firebase_connection.repository.FirebaseInfoRepository
import com.rickmark.seriesviewmanager.data.view_models.SeasonalAnimeViewModel
import com.rickmark.seriesviewmanager.data.view_models.SupportActionBarViewModel
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
import kotlinx.coroutines.launch

class ViewSeasonalAnimeActivity : AppCompatActivity(R.layout.show_seasonal_animes_activity) {

    private val actionBarViewModel: SupportActionBarViewModel by viewModels()
    private val seasonalAnimeViewModel: SeasonalAnimeViewModel by viewModels() { SeasonalAnimeViewModel.Factory }

    private val repository: IFarebaseRespository = FirebaseInfoRepository()

    private lateinit var appNavegation: NavigationManager
    private lateinit var appNavegationCreator: NavegationCreator


    private lateinit var toolbar: Toolbar
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navBottom: BottomNavigationView


    override fun onStart() {
        super.onStart()

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.seasonal_anime_nav_host)
                    as NavHostFragment

        appNavegation = NavigationManager(navHostFragment.navController, resources)

        appNavegationCreator = NavegationCreator(navHostFragment.navController, resources)

        toolbar = findViewById(R.id.seasonal_anime_toolbar)

        navBottom = findViewById(R.id.app_bottom_nav)
    }

    // At the beginning of the app, the list of seasonal anime is loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        repository.getMalToken().addOnSuccessListener { data ->
            lifecycleScope.launch {
                seasonalAnimeViewModel.also {
                    it.loadSeasonalAnimesIfNeeded(data?.value.toString())
                }

                configureSeasonalAnimesNavigation()
                configureNavButton()
            }
        }

    }


    // This activity navigates to each user's profile and to the details of
// a specific anime when tapping on one of the items displayed,
// but only when the app is opened for the first time
    private fun configureSeasonalAnimesNavigation(): Unit {
        val navController: NavController = navHostFragment.navController
        navController.graph = appNavegationCreator.createAppNavGraph()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            appNavegation.changeDestinationChangedListener(
                navController,
                destination,
                null,
                supportActionBar,
                toolbar
            )
        }

        setSupportActionBar(toolbar)
        actionBarViewModel.setToolbar(toolbar)
        actionBarViewModel.setActionBar(supportActionBar)

        val topLevelIds: List<Int> = navController.graph
            .filter(appNavegation::filterTopLevelDestinations)
            .map { it.id }


        val appBarConfiguration =
            AppBarConfiguration.Builder(topLevelDestinationIds = topLevelIds.toSet()).build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }


    private fun configureNavButton() {
        navBottom.menu.clear()
        navBottom.inflateMenu(R.menu.menus_navegation)
        navBottom.setOnItemSelectedListener {
            appNavegationCreator.createNavBottomNavegation(
                it,
                toolbar,
                supportActionBar!!
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_app_bar, menu)
        supportActionBar?.hide()
        return true
    }

}
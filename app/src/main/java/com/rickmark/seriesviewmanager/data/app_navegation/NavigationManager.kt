package com.rickmark.seriesviewmanager.data.app_navegation

import android.content.res.Resources
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isEmpty
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.savedstate.SavedState
import com.rickmark.seriesviewmanager.R

class NavigationManager() {

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


    fun filterTopLevelDestinations(destination: NavDestination): Boolean {
        return destination.route == navegation_profile ||
                destination.route == navegation_seasonal_animes
    }

    fun changeDestinationChangedListener(
        controller: NavController,
        destination: NavDestination,
        arguments: SavedState?,
        actionBar: ActionBar?,
        toolbar: Toolbar
    ) {

        when (destination.route) {
            navegation_seasonal_animes -> {
                actionBar?.hide()
                if (toolbar.menu.isEmpty()) {
                    toolbar.inflateMenu(R.menu.top_app_bar)
                }

            }

            else -> {
                actionBar?.show()
            }
        }
    }


}
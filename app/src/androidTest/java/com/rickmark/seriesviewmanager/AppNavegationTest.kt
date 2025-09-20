package com.rickmark.seriesviewmanager

import android.content.res.Resources
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import com.rickmark.seriesviewmanager.data.app_navegation.NavegationCreator
import com.rickmark.seriesviewmanager.data.app_navegation.NavigationManager
import com.rickmark.seriesviewmanager.ui.seasonalAnime.ViewSeasonalAnimeActivity
import org.junit.Assert
import org.junit.Test

class AppNavegationTest {

    @Test
    fun testAppNavegation() {


        val scenario = ActivityScenario.launch(ViewSeasonalAnimeActivity::class.java)

        scenario.onActivity { activity ->
            val resources: Resources = activity.resources

            Assert.assertNotNull(activity.findViewById(R.id.seasonal_anime_nav_host))

            val navHostFragment =
                activity.supportFragmentManager.findFragmentById(R.id.seasonal_anime_nav_host)
                        as NavHostFragment

            val appNavegation = NavigationManager(navHostFragment.navController, resources)

            val appNavegationCreator = NavegationCreator(navHostFragment.navController, resources)

            val graph = appNavegationCreator.createAppNavGraph()

            Assert.assertNotNull(graph)
            Assert.assertEquals(
                graph.startDestinationRoute,
                resources.getString(R.string.navegation_seasonal_animes)
            )

            val listRoutes: MutableList<String> = mutableListOf(
                resources.getString(R.string.navegation_seasonal_animes),
                resources.getString(R.string.navegation_detail_animes) + "/{anime_id}",
                resources.getString(R.string.navegation_profile)
            )

            listRoutes.forEach { route ->
                Assert.assertNotNull(graph.firstOrNull({ it.route == route }))
            }

            val topLevelDestinations: List<Int> =
                graph.filter(appNavegation::filterTopLevelDestinations).map { it.id }

            Assert.assertNotNull(topLevelDestinations)
            Assert.assertEquals(topLevelDestinations.size, 2)


        }
    }
}
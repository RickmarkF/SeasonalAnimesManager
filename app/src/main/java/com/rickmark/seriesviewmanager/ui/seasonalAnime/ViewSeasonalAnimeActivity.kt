package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.fragment
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager

class ViewSeasonalAnimeActivity : AppCompatActivity(R.layout.activity_view_seasonal_anime) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ShowSeasonalAnimesFragment>(R.id.fragment_container_view)
            }

        }

        var manager: IAnimeManager = AnimeManager()
        manager.getSeasonalAnime()

    }

}
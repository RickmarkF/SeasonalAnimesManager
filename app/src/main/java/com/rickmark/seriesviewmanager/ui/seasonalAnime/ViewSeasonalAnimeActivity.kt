package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.rickmark.seriesviewmanager.R

class ViewSeasonalAnimeActivity : AppCompatActivity(R.layout.activity_view_seasonal_anime) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NavigationSeasonalAnime>(R.id.fragment_container_view)
            }
        }
    }

}
package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.databinding.ActivityViewSeasonalAnimeBinding

class ViewSeasonalAnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewSeasonalAnimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_anime)

        binding = ActivityViewSeasonalAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
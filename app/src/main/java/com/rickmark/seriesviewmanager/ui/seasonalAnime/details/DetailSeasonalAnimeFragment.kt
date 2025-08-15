package com.rickmark.seriesviewmanager.ui.seasonalAnime.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.app_utilities.UiUtilities.Companion.loadImage
import com.rickmark.seriesviewmanager.data.app_utilities.UiUtilities.Companion.updateSpinner
import com.rickmark.seriesviewmanager.data.firebase_connection.repository.FirebaseRepository
import com.rickmark.seriesviewmanager.data.mal_request.MalRequest
import com.rickmark.seriesviewmanager.data.view_models.SupportActionBarViewModel
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.interfaces.mal.IMalRequestManager
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_details.AnimeSeasonDetails
import kotlinx.coroutines.launch

class DetailSeasonalAnimeFragment : Fragment(R.layout.show_detail_seasonal_anime_fragment) {

    private val barViewModel: SupportActionBarViewModel by activityViewModels()
    private val repository: IFarebaseRespository = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animeId: Int? = arguments?.getInt("anime_id")


        val animeImage: ImageView = view.findViewById(R.id.anime_details_anime_image_view)
        val sinopsys: TextView = view.findViewById(R.id.anime_details_synopsis_text_view)
        val startSeason: TextView =
            view.findViewById(R.id.anime_details_start_season_TextView)
        val episodesNum: TextView =
            view.findViewById(R.id.anime_details_num_episodes_TextView)
        val startDate: TextView = view.findViewById(R.id.anime_details_start_date_TextView)
        val alternativeTittlesSpinner: Spinner =
            view.findViewById(R.id.anime_details_alternative_Tittles_Spinner)

        repository.getMalToken().addOnSuccessListener {
            lifecycleScope.launch {

                val manager: IMalRequestManager =
                    MalRequest(token = it?.value.toString(), resources = resources)
                val details: AnimeSeasonDetails? = manager.getAnimeDetails(animeId)

                if (details != null) {

                    val tittleList: ArrayList<String> = details.alternativeTitles.synonyms
                    tittleList.add(details.alternativeTitles.english)
                    tittleList.add(details.alternativeTitles.japanese)


                    startSeason.text = "Anime initial season: ${details.startSeason}"
                    startDate.text = "Anime initial date: ${details.startDate}"
                    episodesNum.text = "Anime episodes number: ${details.numEpisodes}"
                    sinopsys.text = details.synopsis


                    updateSpinner(context, alternativeTittlesSpinner, tittleList)
                    loadImage(view.context, details.mainPicture.large, animeImage)


                    barViewModel.getsetActionBar().show()
                    barViewModel.getToolbar().setOnMenuItemClickListener { menuItem ->
                        writeDataInFirebase(menuItem, details, animeId)
                    }
                }
            }


        }


    }

    private fun writeDataInFirebase(
        menuItem: MenuItem?,
        details: AnimeSeasonDetails,
        animeId: Int?
    ): Boolean = when (menuItem?.itemId) {
        R.id.seasonal_anime_top_bar_save_anime -> {
            repository.writeInFirebase(details.title, animeId)
            true
        }

        else -> false
    }


}




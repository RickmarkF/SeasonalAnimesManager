package com.rickmark.seriesviewmanager.ui.seasonalAnime.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.firebase.repository.FirebaseRepository
import com.rickmark.seriesviewmanager.data.request.MalRequest
import com.rickmark.seriesviewmanager.data.utilities.UiUtilities.Companion.updateSpinner
import com.rickmark.seriesviewmanager.data.view_models.SupportActionBarViewModel
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.interfaces.mal.IMalRequestManager
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_details.AnimeSeasonDetails
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

                val manager: IMalRequestManager = MalRequest(token = it?.value.toString(),resources = resources)
                val details: AnimeSeasonDetails? = manager.getAnimeDetails(animeId)

                if (details != null) {

                    val tittleList: ArrayList<String> = details.alternativeTitles.synonyms
                    tittleList.add(details.alternativeTitles.english)
                    tittleList.add(details.alternativeTitles.japanese)


                    startSeason.text = "Season Inicial del anime: ${details.startSeason}"
                    startDate.text = "Fecha de inicio: ${details.startDate}"
                    episodesNum.text = "Número de episodios: ${details.numEpisodes}"
                    sinopsys.text = details.synopsis


                    updateSpinner(context, alternativeTittlesSpinner, tittleList)
                    Glide.with(view.context).load(details.mainPicture.large)
                        .into(animeImage)


                    barViewModel.getsetActionBar().show()
                    barViewModel.getToolbar().setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.seasonal_anime_top_bar_save_anime -> {
                                repository.writeInFirebase(details.title, animeId)
                                true
                            }

                            else -> false
                        }
                    }
                }
        }



        }


    }}


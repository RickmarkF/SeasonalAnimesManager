package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.FirebaseRepository
import com.rickmark.seriesviewmanager.data.SeasonalAnimeViewModel
import com.rickmark.seriesviewmanager.data.SupportActionBarViewModel
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import kotlinx.coroutines.launch

class DetailSeasonalAnimeFragment : Fragment(R.layout.fragment_detail_seasonal_anime) {

    private val barViewModel: SupportActionBarViewModel by activityViewModels()
    private val repository: IFarebaseRespository = FirebaseRepository()
    private val seasonalAnimeViewModel: SeasonalAnimeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barViewModel.getsetActionBar().show()
        val animeId: Int? = arguments?.getInt("anime_id")
        val nav = findNavController()
        val manager: IAnimeManager = AnimeManager()

        lifecycleScope.launch {
            val data: AnimeDetails? = try {
                manager.getAnimeDetails(animeId)
            } catch (e: Exception) {
                Log.e("DetailSeasonalAnimeFragment", "Error fetching anime details", e)
                nav.navigateUp()
                null
            }

            if (data != null) {
                val animeImage: ImageView = view.findViewById(R.id.imageSeasonalAnime)
                val sinopsys: TextView = view.findViewById(R.id.sinopsysSeasonalAnime)
                val startSeason: TextView = view.findViewById(R.id.start_season)
                val episodesNum: TextView = view.findViewById(R.id.num_episodes)
                val startDate: TextView = view.findViewById(R.id.start_date)
                val alternativeTittlesSpinner: Spinner =
                    view.findViewById(R.id.alternativeTittlesSpinner)

                val tittleList: ArrayList<String> = data.alternativeTitles.synonyms
                tittleList.add(data?.alternativeTitles!!.english)
                tittleList.add(data?.alternativeTitles!!.japanese)

                startSeason.text = "Season Inicial del anime: ${data.startSeason}"
                startDate.text = "Fecha de inicio: ${data.startDate}"
                episodesNum.text = "Número de episodios: ${data.numEpisodes}"

                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(
                        view.context, android.R.layout.simple_spinner_item,
                        tittleList
                    )
                alternativeTittlesSpinner.adapter = adapter
                Glide.with(view.context).load(data?.mainPicture?.large).into(animeImage);
                sinopsys.text = data?.synopsis

                barViewModel.getToolbar().setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.seasonalAnime -> {
                            repository.writeInFirebase(data.title, animeId)
                            true
                        }

                        else -> false
                    }
                }
            }

        }


    }

}
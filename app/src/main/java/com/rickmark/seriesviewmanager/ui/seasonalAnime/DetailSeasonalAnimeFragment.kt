package com.rickmark.seriesviewmanager.ui.seasonalAnime

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.SupportActionBarViewModel
import com.rickmark.seriesviewmanager.data.request.AnimeManager
import com.rickmark.seriesviewmanager.domain.interfaces.IAnimeManager
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails

class DetailSeasonalAnimeFragment : Fragment(R.layout.fragment_detail_seasonal_anime) {

    private val barViewModel: SupportActionBarViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barViewModel.getsetActionBar().show()
        val animeId: Int? = arguments?.getInt("anime_id")
        val nav = findNavController()

        val manager: IAnimeManager = AnimeManager()
        val data: AnimeDetails? = manager.getAnimeDetails(animeId)

        val animeImage: ImageView = view.findViewById(R.id.imageSeasonalAnime)
        val sinopsys: TextView = view.findViewById(R.id.sinopsysSeasonalAnime)
        val alternativeTittlesSpinner: Spinner = view.findViewById(R.id.alternativeTittlesSpinner)

        val tittleList: ArrayList<String> = data?.alternativeTitles!!.synonyms
        tittleList.add(data?.alternativeTitles!!.english)
        tittleList.add(data?.alternativeTitles!!.japanese)

        val adapter: ArrayAdapter<String> =
            ArrayAdapter(
                view.context, android.R.layout.simple_spinner_item,
                tittleList
            )
        alternativeTittlesSpinner.adapter = adapter
        Glide.with(this).load(data?.mainPicture?.large).into(animeImage);
        sinopsys.text = data?.synopsis

    }

}
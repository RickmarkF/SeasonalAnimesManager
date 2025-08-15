package com.rickmark.seriesviewmanager.ui.seasonalAnime.show

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.app_utilities.UiUtilities.Companion.loadImage
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_list.Data

class ShowSeasonalAnimesAdapter(
    private val dataSet: List<Data>,
    private val context: Context
) :
    RecyclerView.Adapter<ShowSeasonalAnimesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var texto: TextView = view.findViewById(R.id.seasonal_anime_item_name_text_view)
        var imagen: ImageView = view.findViewById(R.id.seasonal_anime_item_image_view)

        lateinit var data: Data

        init {
            val resources: Resources = context.resources

            val anime_detail_navegation: String =
                resources.getString(R.string.navegation_detail_animes)

            imagen.setOnClickListener {
                val navHostFragment = view.findNavController()
                navHostFragment.navigate("$anime_detail_navegation/${data.node.id}")
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.show_seasonal_animes_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.texto.text = dataSet[position].node.title
        holder.data = dataSet[position]
        val image: String? = dataSet[position].node.mainPicture.large
        if (!image.isNullOrEmpty()) {
            loadImage(context, image, holder.imagen)
        }


    }

    override fun getItemCount(): Int = dataSet.size

}
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
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_anime_list.Data

class SeasonalAnimeRecyclerAdapter(
    private val dataSet: List<Data>,
    private val context: Context,
    private val resources: Resources
) :
    RecyclerView.Adapter<SeasonalAnimeRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var texto: TextView = view.findViewById(R.id.seasonal_anime_item_name_text_view)
        var imagen: ImageView = view.findViewById(R.id.seasonal_anime_item_image_view)

        lateinit var data: Data

        init {

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
        val image: String? = dataSet[position].node.mainPicture?.large.toString()
        holder.data = dataSet[position]
        if (!image.isNullOrEmpty()) {
            Glide.with(context).load(image).into(holder.imagen);
        }


    }

    override fun getItemCount(): Int = dataSet.size

}
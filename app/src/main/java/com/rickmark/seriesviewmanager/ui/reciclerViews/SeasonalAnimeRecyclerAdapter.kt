package com.rickmark.seriesviewmanager.ui.reciclerViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.domain.constants.NavegationRutes
import com.rickmark.seriesviewmanager.domain.models.Data

class SeasonalAnimeRecyclerAdapter(private val dataSet: List<Data>, private val context: Context) :
    RecyclerView.Adapter<SeasonalAnimeRecyclerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var texto: TextView = view.findViewById(R.id.animeName)
        var imagen: ImageView = view.findViewById(R.id.animeImage)

        lateinit var data: Data

        init {
            imagen.setOnClickListener {
                val navHostFragment = view.findNavController()
                navHostFragment.navigate("${NavegationRutes.FRAGMENT_ANIME_DETAIL}/${data.node.id}")
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.lista_temporada, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.texto.text = dataSet[position].node.title
        val image: String? = dataSet[position].node.mainPicture?.large.toString()
        holder.data = dataSet[position]
        if (!image.isNullOrEmpty()) {
            Glide.with(context).load(image).into(holder.imagen);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = dataSet.size

}
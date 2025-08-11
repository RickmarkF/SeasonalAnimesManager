package com.rickmark.seriesviewmanager.ui.reciclerViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.Data

class MyProfileRecyclerAdapter(private val animes: List<Data>,
                               private val c: Context
) :
    RecyclerView.Adapter<MyProfileRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_data, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.textView.text = animes[position].node.title
        Glide.with(c).load(animes[position].node.mainPicture.large).into(holder.imageView)
    }

    override fun getItemCount(): Int = animes.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.anime_name)
        val imageView: ImageView = view.findViewById(R.id.anime_imagen)

    }
}
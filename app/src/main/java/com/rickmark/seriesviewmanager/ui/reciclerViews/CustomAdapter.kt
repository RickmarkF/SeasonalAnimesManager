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
import com.rickmark.seriesviewmanager.domain.models.Data
import kotlinx.serialization.ExperimentalSerializationApi

class CustomAdapter(private val dataSet: List<Data>, private val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_data, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.anime_imagen)
        val textView: TextView = view.findViewById(R.id.anime_name)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position].node.title
        val image: String? = dataSet[position].node.mainPicture?.large.toString()

        if (!image.isNullOrEmpty()) {
            Glide.with(context).load(image).into(holder.imageView);
        }
    }

    override fun getItemCount() = dataSet.size
}

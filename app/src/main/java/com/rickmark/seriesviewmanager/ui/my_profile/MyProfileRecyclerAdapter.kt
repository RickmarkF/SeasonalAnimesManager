package com.rickmark.seriesviewmanager.ui.my_profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.app_utilities.UiUtilities.Companion.createAlertDialoge
import com.rickmark.seriesviewmanager.data.app_utilities.UiUtilities.Companion.loadImage
import com.rickmark.seriesviewmanager.data.firebase_connection.repository.FirebaseRepository
import com.rickmark.seriesviewmanager.domain.interfaces.firebase.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.pojos.seasonal_animes.anime_list.Data

class MyProfileRecyclerAdapter(
    private val animes: MutableList<Data>,
    private val context: Context
) : RecyclerView.Adapter<MyProfileRecyclerAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repository: IFarebaseRespository = FirebaseRepository()
        val textView: TextView = view.findViewById(R.id.my_profile_anime_saved_anime_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.my_profile_anime_saved_anime_image_view)

        var animeID: Int = 0

        init {
            view.setOnClickListener {
                val text = textView.text

                val dialog: AlertDialog = createAlertDialoge(
                    context = view.context,
                    title = "Delete anime",
                    message = "Are you sure you want to delete $text from your whislist?",
                    positiveAction = { _, _ ->
                        animes.removeIf { it.node.id.toInt() == animeID }
                        notifyDataSetChanged()
                        repository.deleteFromFirebase(text.toString())
                    }
                )
                dialog.show()
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_profile_anime_saved, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.textView.text = animes[position].node.title
        holder.animeID = animes[position].node.id.toInt()
        loadImage(context, animes[position].node.mainPicture.large, holder.imageView)
    }

    override fun getItemCount(): Int = animes.size


}
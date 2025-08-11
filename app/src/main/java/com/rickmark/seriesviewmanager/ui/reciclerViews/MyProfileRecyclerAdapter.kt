package com.rickmark.seriesviewmanager.ui.reciclerViews

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickmark.seriesviewmanager.R
import com.rickmark.seriesviewmanager.data.FirebaseRepository
import com.rickmark.seriesviewmanager.domain.interfaces.IFarebaseRespository
import com.rickmark.seriesviewmanager.domain.models.AnimeDetails
import com.rickmark.seriesviewmanager.domain.models.Data

class MyProfileRecyclerAdapter(private val animes: MutableList<Data>,
                               private val c: Context
) : RecyclerView.Adapter<MyProfileRecyclerAdapter.ViewHolder>() {


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
        holder.animeID = animes[position].node.id.toInt()
    }

    override fun getItemCount(): Int = animes.size

   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val repository : IFarebaseRespository = FirebaseRepository()
        val textView: TextView = view.findViewById(R.id.anime_name)
        val imageView: ImageView = view.findViewById(R.id.anime_imagen)

        var animeID: Int = 0

        init {
            view.setOnClickListener {
                val text = textView.text

                val builder: AlertDialog.Builder = AlertDialog.Builder(c)
                builder
                    .setMessage("¿Quieres eliminar este anime?: $text")
                    .setTitle("Eliminar anime")
                    .setPositiveButton("Si") { dialog, which ->
                        animes.removeIf { it.node.id.toInt() == animeID }
                        notifyDataSetChanged()
                        repository.deleteFromFirebase(text.toString())

                    }
                    .setNegativeButton("No"){dialog, which ->}

                val dialog: AlertDialog = builder.create()
                dialog.show()
                Log.d("RecyclerView", text.toString())
                Log.d("RecyclerView", "CLICK!")
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(data: Data) {
        animes.remove(data)
        notifyDataSetChanged()
    }
}


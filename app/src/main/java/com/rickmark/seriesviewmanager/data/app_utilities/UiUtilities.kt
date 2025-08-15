package com.rickmark.seriesviewmanager.data.app_utilities

import android.content.Context
import android.content.DialogInterface
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide

class UiUtilities {

    companion object {
        fun updateSpinner(context: Context?, spinner: Spinner, list: MutableList<String>) {
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item, list
                )
            }
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }

        fun createAlertDialoge(
            context: Context,
            title: String,
            message: String,
            positiveButton: String = "Yes",
            negativeButton: String = "No",
            positiveAction: (dialog: DialogInterface, wich: Int) -> Unit = { dialog, wich ->
                {}
            },
            negativeAction: (dialog: DialogInterface, wich: Int) -> Unit = { dialog, wich ->
                {}
            }
        ): AlertDialog {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(positiveButton, positiveAction)
                .setNegativeButton(negativeButton, negativeAction)

            return builder.create()
        }

        fun loadImage(context: Context, url: String, imageView: ImageView) {
            Glide.with(context).load(url).into(imageView)
        }
    }
}
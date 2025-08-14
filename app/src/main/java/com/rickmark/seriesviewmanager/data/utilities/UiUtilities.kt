package com.rickmark.seriesviewmanager.data.utilities

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

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
    }
}
package com.rickmark.seriesviewmanager.data

import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModel

class SupportActionBarViewModel : ViewModel() {

    private lateinit var actionBar: ActionBar

    fun setActionBar(actionBar: ActionBar?) {
        if (actionBar != null) {
            this.actionBar = actionBar
        }
    }

    fun getsetActionBar(): ActionBar {
        return actionBar
    }


}
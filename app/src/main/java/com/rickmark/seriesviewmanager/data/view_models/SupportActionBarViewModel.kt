package com.rickmark.seriesviewmanager.data.view_models


import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel

// This ViewModel manages showing and hiding the ActionBar
// and Toolbar based on the currently displayed fragment
class SupportActionBarViewModel : ViewModel() {

    private lateinit var actionBar: ActionBar
    private lateinit var toolbar: Toolbar

    fun setToolbar(toolbar: Toolbar) {
        this.toolbar = toolbar
    }

    fun getToolbar(): Toolbar {
        return toolbar
    }


    fun setActionBar(actionBar: ActionBar?) {
        if (actionBar != null) {
            this.actionBar = actionBar
        }
    }

    fun getsetActionBar(): ActionBar {
        return actionBar
    }


}
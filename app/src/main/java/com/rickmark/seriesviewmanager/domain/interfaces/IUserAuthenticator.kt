package com.rickmark.seriesviewmanager.domain.interfaces

import android.content.Context

interface IUserAuthenticator {

   fun createUser(email: String, password: String): Unit
   fun loginUser(email: String, password: String): Unit
}
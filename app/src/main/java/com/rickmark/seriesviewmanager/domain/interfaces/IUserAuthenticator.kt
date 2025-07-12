package com.rickmark.seriesviewmanager.domain.interfaces

interface IUserAuthenticator {

    fun createUser(email: String, password: String): Unit
    fun loginUser(email: String, password: String): Unit
}
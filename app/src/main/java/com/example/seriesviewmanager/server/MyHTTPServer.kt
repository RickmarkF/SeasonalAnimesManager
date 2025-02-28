package com.example.seriesviewmanager.server

import fi.iki.elonen.NanoHTTPD

class MyHTTPServer(port: Int) : NanoHTTPD(port) {


    override fun serve(session: IHTTPSession): Response {
        val uri:String = session.uri
        return MyServerResponse.getResponse(uri,session)
    }
}
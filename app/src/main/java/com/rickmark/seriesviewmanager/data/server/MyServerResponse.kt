package com.rickmark.seriesviewmanager.data.server

import fi.iki.elonen.NanoHTTPD.Response
import fi.iki.elonen.NanoHTTPD.newFixedLengthResponse

class MyServerResponse {
    companion object {
        fun getResponse(uri: String): Response {
            return if (uri == "/callback") {
                newFixedLengthResponse("Código de autorización recibido:")
            } else {
                newFixedLengthResponse("404 Not Found")
            }
        }


    }
}
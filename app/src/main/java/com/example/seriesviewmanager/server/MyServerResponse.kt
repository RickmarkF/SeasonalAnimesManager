package com.example.seriesviewmanager.server

import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.Response
import fi.iki.elonen.NanoHTTPD.newFixedLengthResponse

class MyServerResponse {
    companion object{
        fun getResponse(uri: String, session: NanoHTTPD.IHTTPSession):Response{
            if (uri == "/callback") {
               return newFixedLengthResponse("Código de autorización recibido:")
            } else {
               return newFixedLengthResponse("404 Not Found")
            }
        }



    }
}
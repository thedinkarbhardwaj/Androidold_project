package com.example.googlemap

data class RequestData(
    val latitud: String,
    val longitud: String,
    val usuarioId: Int,
    val fechaSolicitud: String,
    val numeroSubzonas: Int
)

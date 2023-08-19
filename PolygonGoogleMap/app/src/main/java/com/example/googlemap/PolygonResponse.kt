package com.example.googlemap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 class PolygonResponse {
    @SerializedName("Estado")
    @Expose
    var estado: String? = null

    @SerializedName("numeroSubzonas")
    @Expose
    var numeroSubzonas: Int? = null

    @SerializedName("fechaSolicitud")
    @Expose
    var fechaSolicitud: String? = null

    @SerializedName("subZonas")
    @Expose
    var subZonas: List<SubZona>? = null

    @SerializedName("Mensajes")
    @Expose
    var mensajes: List<Any>? = null

    class SubZona {
        @SerializedName("subZonaId")
        @Expose
        var subZonaId: Int? = null

        @SerializedName("nombre")
        @Expose
        var nombre: String? = null

        @SerializedName("centro")
        @Expose
        var centro: Centro? = null

        @SerializedName("poligono")
        @Expose
        var poligono: List<Poligono>? = null

        @SerializedName("totalEspacios")
        @Expose
        var totalEspacios: Int? = null

        @SerializedName("porcentajeOcupacion")
        @Expose
        var porcentajeOcupacion: Int? = null

        @SerializedName("mensaje")
        @Expose
        var mensaje: String? = null

        @SerializedName("marca")
        @Expose
        var marca: String? = null

        class Centro {
            @SerializedName("latitud")
            @Expose
            var latitud: Double? = null

            @SerializedName("longitud")
            @Expose
            var longitud: Double? = null
        }

        class Poligono {
            @SerializedName("latitud")
            @Expose
            var latitud: Double? = null

            @SerializedName("longitud")
            @Expose
            var longitud: Double? = null
        }
    }


}


package com.example.jsondatafetch

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface apiInterface {

    @GET("users")
    fun users():Call<JsonArray>
}
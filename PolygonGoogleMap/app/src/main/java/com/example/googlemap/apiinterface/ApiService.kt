package com.example.googlemap.apiinterface

import com.example.googlemap.PolygonResponse
import com.example.googlemap.RequestData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HEAD
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("ZonaAzulApi/ZonaAzul/GetAvailabilitySubzones")
    @Headers(
        "Content-Type: application/json",
        "token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiaWdvcmFwcCIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvZXhwaXJhdGlvbiI6IjIwMjMtMDgtMTcgMTM6MzM6NDEiLCJleHAiOjE2OTIyNzkyMjEsImlzcyI6Imh0dHBzOi8vMTkyLjEwLjIwMC4xOCIsImF1ZCI6Imh0dHBzOi8vMTkyLjEwLjIwMC4xOCJ9.98k2dd_3XYANmDUAi9I52k63_qdm3S8BBznbNh1_O50",
        "Username: igorapp"
    )
    fun sendData(@Body requestData: RequestData): Call<PolygonResponse>
}

package com.rtoexamtest.ktor.Network

import com.google.gson.Gson
import com.rtoexamtest.ktor.data.Post
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.get
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class ApiService @Inject constructor() {

    val client = HttpClient(Android){
        install(DefaultRequest){
            headers.append("Content-Type","application/json")
        }

        install(JsonFeature){
            serializer = GsonSerializer()
        }

        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }

    suspend fun getPost():List<Post>{
        return client.get{
           url("https://jsonplaceholder.typicode.com/posts")
        }
    }
}